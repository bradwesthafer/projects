;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;      CMPSC 460-001                                         ;;;
;;;      Homework 4                                            ;;;
;;;      Brad Westhafer                                        ;;;
;;;      bdw5204                                               ;;;
;;;                                                            ;;;
;;;      Note:                                                 ;;;
;;;                                                            ;;;
;;;                                                            ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



(module hw4 (lib "eopl.ss" "eopl")

  (provide
    run
    expval->num
    expval->bool
    expval->proc
    num-val
    bool-val
    )


  ;;;;   Program    ::= Expression
  ;;;;                  [[ a-program (exp1) ]]
  ;;;;   Expression ::= Number
  ;;;;                  [[ const-exp (num) ]]
  ;;;;                ::= Identifier
  ;;;;                  [[ var-exp (var) ]]
  ;;;;                ::= zero? ( Expression )
  ;;;;                  [[ zero?-exp (exp1) ]]
  ;;;;                ::= - ( Expression , Expression )
  ;;;;                  [[ diff-exp (exp1 exp2) ]]
  ;;;;                ::= if Expression then Expression else Expression
  ;;;;                  [[ if-exp (exp1 exp2 exp3) ]]
  ;;;;                ::= let {Identifier = Expression}* in Expression
  ;;;;                  [[ let-exp (vars exps body) ]]
  ;;;;                ::= proc ( {Identifier}*(,) ) Expression
  ;;;;                  [[ proc-exp (vars body) ]]
  ;;;;                ::= (Expression {Expression}*)
  ;;;;                  [[ call-exp (rator rands) ]]
  ;;;;                ::= letrec Identifier (Identifier) = Expression in Expression
  ;;;;                  [[ letrec-exp (p-name b-var p-body letrec-body) ]]
  ;;;;                ::= * ( Expression , Expression )
  ;;;;                  [[ mult-exp (exp1 exp2) ]]
  ;;;;                ::= / ( Expression , Expression )
  ;;;;                  [[ div-exp (exp1 exp2) ]]
  ;;;;                ::= dynproc ( Identifier ) Expression
  ;;;;                  [[ dynproc-exp (var body) ]]
  
  
  
  ;;;;
  ;;;; Lexical Specification
  ;;;; - DO NOT CHANGE
  (define the-lexical-spec
    '((whitespace (whitespace) skip)
      (comment ("%" (arbno (not #\newline))) skip)
      (identifier
        (letter (arbno (or letter digit "_" "-" "?")))
        symbol)
      (number (digit (arbno digit)) number)
      (number ("-" digit (arbno digit)) number)
      ))


  ;;;;
  ;;;; Grammar
  ;;;;
  (define the-grammar
    '(
      (program (expression) a-program)
      (expression (number) const-exp)
      (expression (identifier) var-exp)
      (expression
        ("zero?" "(" expression ")")
        zero?-exp)
      (expression
        ("-" "(" expression "," expression ")")
        diff-exp)
      (expression
        ("if" expression "then" expression "else" expression)
        if-exp)
      (expression
        ("let" (arbno identifier "=" expression) "in" expression)
        let-exp)
      (expression
        ("proc" "(" (separated-list identifier ",") ")" expression)
        proc-exp)
      (expression
        ("(" expression (arbno expression) ")")
        call-exp)
      (expression
        ("letrec" identifier "(" identifier ")" "=" expression "in" expression)
        letrec-exp)
      (expression
       ("*" "(" expression "," expression ")")
       mult-exp)
      (expression
       ("/" "(" expression "," expression ")")
       div-exp)
      (expression
       ("dynproc" "(" identifier ")" expression)
       dynproc-exp)
      ))



  ;;;; generate the data types for each production in the grammar
  ;;;; - constructors:
  ;;;;     a-program : Exp -> Program
  ;;;;     const-exp : Int -> Exp
  ;;;;     var-exp   : Var -> Exp
  ;;;;     zero?-exp : Exp -> Exp
  ;;;;     diff-exp  : Exp x Exp -> Exp
  ;;;;     if-exp    : Exp x Exp x Exp -> Exp
  ;;;;     let-exp   : Var x Exp x Exp -> Exp
  ;;;;     proc-exp  : Var x Exp -> Exp
  ;;;;     call-exp  : Exp x Exp -> Exp
  ;;;;     letrec-exp: Var x Var x Exp x Exp -> Exp
  ;;;;     mult-exp  : Exp x Exp -> Exp
  ;;;;     div-exp   : Exp x Exp -> Exp
  (sllgen:make-define-datatypes the-lexical-spec the-grammar)


  ;;;; list the data types generated from lexical spec and grammar
  (define show-the-datatypes
    (lambda ()
      (sllgen:list-define-datatypes the-lexical-spec the-grammar)))


  ;;;; generates a parser that
  ;;;;   scans program text according to the lexical spec and
  ;;;;   parses the generated tokens according to the grammar
  (define scan&parse
    (sllgen:make-string-parser the-lexical-spec the-grammar))

  ;;;; generate the scanner based on lexical spec and grammar
  (define just-scan
    (sllgen:make-string-scanner the-lexical-spec the-grammar))




  ;;;; Values for our language
  ;;;;      ExpVal = Int + Bool + Proc
  ;;;;      DenVal = Int + Bool + Proc


  ;; data type for ExpVal
  ;;
  ;; Expressed value is either a number, a boolean, or a procedure
  (define-datatype expval expval?
    (num-val (num number?))
    (bool-val (bool boolean?))
    (proc-val (proc proc?)))

  ;; The values for number and boolean simply use Scheme's number and boolean
  ;; So, only need to define datatype for proc
  ;;
  ;;
  ;; Data type for Proc
  (define-datatype proc proc?
    (procedure (vars list?)
               (body expression?)
               (env environment?))
    (dynproc (var symbol?)
             (body expression?)))


  ;; expval extractors
  ;;
  ;; expval->num : ExpVal -> Int
  (define expval->num
    (lambda (e-val)
      (cases expval e-val
        (num-val (num) num)
        (else (expval-extractor-error 'num e-val)))))

  ;; expval->bool : ExpVal -> Bool
  (define expval->bool
    (lambda (e-val)
      (cases expval e-val
        (bool-val (bool) bool)
        (else (expval-extractor-error 'bool e-val)))))

  ;; expval->proc : ExpVal -> Proc
  (define expval->proc
    (lambda (e-val)
      (cases expval e-val
        (proc-val (proc) proc)
        (else (expval-extractor-error 'proc e-val)))))

  ;; helper for extractors to report error
  (define expval-extractor-error
    (lambda (variant val)
      (eopl:error
        'expval-extractors
        "Looking for a ~s, found ~s" variant val)))



  ;;;; environment
  ;;;; - needed to evaluate expressions containing variables
  ;;;; - keeps the value associated with each variable
  ;;;; - environment is a function
  ;;;;      Env : Var -> DenVal
  
  ;; Data structure for environment
  (define-datatype environment environment?
    (empty-env)
    (extend-env (vars list?) ;of symbols
                (vals list?) ;of expvals
                (saved-env environment?))
    (extend-env-rec (p-name symbol?)
                    (b-var symbol?)
                    (p-body expression?)
                    (saved-env environment?)))

  ;; apply-env : Env x Var -> ExpVal
  (define apply-env
    (lambda (env search-var)
      (cases environment env
        (empty-env () (eopl:error 'apply-env "No binding for ~s" search-var))
        (extend-env (vars vals saved-env)
                    (apply-extend-env vars vals saved-env search-var))
        (extend-env-rec (p-name b-var p-body saved-env)
                        (if (eqv? search-var p-name)
                            (proc-val (procedure (list b-var) p-body env))
                            (apply-env saved-env search-var))))))

  (define apply-extend-env
    (lambda (vars vals saved-env search-var)
      (cond
        ((null? vars) (apply-env saved-env search-var))
        ((eqv? search-var (car vars)) (car vals))
        (else (apply-extend-env (cdr vars) (cdr vals) saved-env search-var)))))


  ;; initial environment for testing
  ;; init-env : () -> Env
  ;;
  ;; - DO NOT CHANGE
  (define init-env
    (lambda ()
      (extend-env '(i) (list (num-val 1))
        (extend-env '(v) (list (num-val 5))
          (extend-env '(x) (list (num-val 10))
            (empty-env))))))



  
  ;;;;
  ;;;; Interpreter for our language
  ;;;;
  
  
  ;; run : String -> ExpVal
  ;;
  (define run
    (lambda (str)
      (value-of-program (scan&parse str))))

  ;; value-of-program : Program -> ExpVal
  (define value-of-program
    (lambda (pgm)
      (cases program pgm
        (a-program (exp)
                   (value-of exp (init-env))))))

  ;; value-of : Expression x Environment -> ExpVal
  (define value-of
    (lambda (exp env)
      (cases expression exp
        (const-exp (num) (num-val num))
        (var-exp (var) (apply-env env var))
        (zero?-exp (exp)
                  (let ((val (value-of exp env)))
                    (let ((num (expval->num val)))
                      (if (zero? num)
                          (bool-val #t)
                          (bool-val #f)))))
        (diff-exp (exp1 exp2)
                  (let ((val1 (value-of exp1 env))
                        (val2 (value-of exp2 env)))
                    (let ((num1 (expval->num val1))
                          (num2 (expval->num val2)))
                      (num-val (- num1 num2)))))
        (if-exp (test-exp then-exp else-exp)
                (let ((test-val (value-of test-exp env)))
                  (if (expval->bool test-val)
                      (value-of then-exp env)
                      (value-of else-exp env))))
        (let-exp (vars exps body)
                 (let ((vals (getvals exps env '())))
                   (value-of body (extend-env vars vals env))))
        (proc-exp (vars body)
                  (proc-val (procedure vars body env)))
        (call-exp (rator rands)
                  (let ((func (expval->proc (value-of rator env)))
                        (args (getargs rands env '())))
                    (apply-procedure func args env)))
        (letrec-exp (p-name b-var p-body letrec-body)
                    (value-of letrec-body
                              (extend-env-rec p-name b-var p-body env)))
        (mult-exp (exp1 exp2)
                  (let ((val1 (value-of exp1 env))
                        (val2 (value-of exp2 env)))
                    (let ((num1 (expval->num val1))
                          (num2 (expval->num val2)))
                      (num-val (* num1 num2)))))
        (div-exp (exp1 exp2)
                  (let ((val1 (value-of exp1 env))
                        (val2 (value-of exp2 env)))
                    (let ((num1 (expval->num val1))
                          (num2 (expval->num val2)))
                      (if (equal? 0 num2)
                          (div-exp-exception)
                          (num-val (quotient num1 num2))))))
        (dynproc-exp (var body)
                     (proc-val (dynproc var body))))))

  (define div-exp-exception
    (lambda ()
      (eopl:error
       'div-exp
       "Divide by Zero Exception")))

  (define getvals
    (lambda (exps env retVal)
      (cond
        ((null? exps) (reverse retVal))
        (else (getvals (cdr exps) env (cons (value-of (car exps) env) retVal))))))

  (define getargs
    (lambda (rands env retVal)
      (cond
        ((null? rands) (reverse retVal))
        (else (getargs (cdr rands) env (cons (value-of (car rands) env) retVal))))))

  
  ;; apply-procedure: Proc x ExpVal -> ExpVal
  (define apply-procedure
    (lambda (func args dynenv)
      (cases proc func
        (procedure (vars body env)
                   (value-of body (extend-env vars args env)))
        (dynproc (var body)
                 (value-of body (extend-env var args dynenv))))))



  
  
  ;;;; DO NOT REMOVE THE FOLLOWING PARENTHESIS
)
