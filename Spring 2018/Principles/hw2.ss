;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; CMPSC 460-001                        ;;;
;;; Homework 2                           ;;;
;;; Brad Westhafer                       ;;;
;;; bdw5204                              ;;;
;;;                                      ;;;
;;; Note:                                ;;;
;;;                                      ;;;
;;;                                      ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(module hw2 (lib "eopl.ss" "eopl")
  
  (provide
   s-list->scheme-list
   scheme-list->s-list
   s-exp->scheme-exp
   scheme-exp->s-exp
   subst
   scheme-exp->lc-exp
   lc-exp->scheme-exp
   free-vars
   bound-vars
   )
  
  
  ;; S-List
  ;;
  ;; <s-list> ::= ( {s-exp}* )  <== () | ( <s-exp> . <s-list> )
  ;;              [[ empty-s-list ]]  |  [[ non-empty-s-list (s-exp s-list) ]]
  ;; <s-exp>  ::= <symbol> | <s-list>
  ;;              [[ symbol-s-exp (sym) ]]  |  [[ s-list-s-exp (slist) ]]
  
  ;; Define the necessary data types and procedures for S-List
  ;; conforming to both concrete as well as abstract syntax above
  
  (define-datatype s-list s-list?
    (empty-s-list)
    (non-empty-s-list (first s-exp?)
                      (rest s-list?)))
  (define-datatype s-exp s-exp?
    (symbol-s-exp (sym symbol?))
    (s-list-s-exp (slist s-list?)))
  ;scheme-list->s-list: Scheme-List -> S-List
  (define scheme-list->s-list
    (lambda (lst)
      (cond
        ((null? lst) (empty-s-list))
        (else
         (non-empty-s-list
          (scheme-exp->s-exp (car lst))
          (scheme-list->s-list (cdr lst)))))))
  ;s-list->scheme-list: S-List -> Scheme-List
  (define s-list->scheme-list
    (lambda (slst)
      (cases s-list slst
        (empty-s-list () '())
        (non-empty-s-list (first rest)
                          (cons (s-exp->scheme-exp first)
                                (s-list->scheme-list rest))))))
  ;scheme-exp->s-exp: Scheme-Exp -> S-Exp
  (define scheme-exp->s-exp
    (lambda (exp)
      (cond
        ((symbol? exp) (symbol-s-exp exp))
        ((list? exp) (s-list-s-exp (scheme-list->s-list exp))))))
  ;s-exp->scheme-exp: S-Exp -> Scheme-Exp
  (define s-exp->scheme-exp
    (lambda (sexp)
      (cases s-exp sexp
        (symbol-s-exp (sym) sym)
        (s-list-s-exp (slst) (s-list->scheme-list slst)))))
  ;subst: Symbol-S-Exp x Symbol-S-Exp x S-List -> S-List
  (define subst
    (lambda (old new slst)
      (cases s-list slst
        (empty-s-list () (empty-s-list))
        (non-empty-s-list (first rest) (cond
                                         ((eqv? (s-exp->scheme-exp first) (s-exp->scheme-exp old)) (non-empty-s-list new (subst old new rest)))
                                         (else (non-empty-s-list first (subst old new rest))))))))
  
  
  
  ;; Lc-exp
  ;;
  ;; Lc-exp ::= Identifier
  ;;            [[ var-exp (var) ]]
  ;;        ::= (lambda ( {Identifier}* ) Lc-exp)
  ;;            [[ lambda-exp (bound-vars body) ]]
  ;;        ::= ( Lc-exp {Lc-exp}* )
  ;;            [[ app-exp (rator rands) ]]
  
  (define identifier? symbol?)
  
  ;; Write the abstract syntax for Lc-exp whose concrete syntax is give above
  ;; (See S-List as an example)
  ;;
  ;; Then, define the necessary data types and procedures for Lc-exp
  ;; conforming to both concrete syntax above and your  abstract syntax.
  
  (define-datatype lc-exp lc-exp?
    (var-exp (var identifier?))
    (lambda-exp (bound-var list?)
                (body lc-exp?))
    (app-exp (rator lc-exp?)
             (rand lc-exp?)))
  ;scheme-exp->lc-exp: Scheme-Exp -> Lc-Exp
  (define scheme-exp->lc-exp
    (lambda (exp)
      (cond
        ((null? exp) (var-exp 'nullouweghgeogghggouegggoigehogeoioghewoihewgohiweghoigejbnbfe))
        ((identifier? exp) (var-exp exp))
        ((and (eqv? (car exp) 'lambda) (list? (cadr exp))) (lambda-exp (cadr exp) (scheme-exp->lc-exp (caddr exp))))
        (else (app-exp (scheme-exp->lc-exp (car exp)) (scheme-exp->lc-exp (cdr exp)))))))
  ;lc-exp->scheme-exp: Lc-Exp -> Scheme-Exp
  (define lc-exp->scheme-exp
    (lambda (lcexp)
      (cases lc-exp lcexp
        (var-exp (var) (cond
                         ((eqv? var 'nullouweghgeogghggouegggoigehogeoioghewoihewgohiweghoigejbnbfe) '())
                         (else var)))
        (lambda-exp (bound-var body) (list 'lambda bound-var (lc-exp->scheme-exp body)))
        (app-exp (rator rand) (cons (lc-exp->scheme-exp rator) (lc-exp->scheme-exp rand))))))
  
  ;utility for free-vars and bound-vars
  ;in-body?: Scheme-Exp X Scheme-List-of-Symbols -> Boolean
  (define in-body?
    (lambda (search-var body)
      (cond
        ((null? body) #f)
        ((eqv? (car body) search-var) #t)
        (else (in-body? search-var (cdr body))))))
  ;free-vars: Lc-Exp -> Scheme-List-of-Symbols
  (define free-vars
    (lambda (lcexp)
      (cases lc-exp lcexp
        (var-exp (var) (cond
                         ((eqv? 'nullouweghgeogghggouegggoigehogeoioghewoihewgohiweghoigejbnbfe var) '())
                         (else (list var))))
        ;help: Scheme-List-Of-Symbols X Scheme-List-of-Symbols X Scheme-List-of-Symbols -> Scheme-List-Of-Symbols
        ;recursive helper function stores a list of all symbols to return in ret-val, iterates through list and returns ret-val when done
        (lambda-exp (bound-var body) (letrec ((help
                                               (lambda (bound-var free ret-val)
                                                 (cond
                                                   ((null? free) ret-val)
                                                   ((and (identifier? (car free)) (in-body? (car free) bound-var)) (help bound-var (cdr free) ret-val))
                                                   ((identifier? (car free)) (help bound-var (cdr free) (cons (car free) ret-val)))
                                                   (else (help bound-var (cdr free) (append (help bound-var (car free) '()) ret-val)))))))
                                       (help bound-var (free-vars body) '())))
        (app-exp (rator rand) (append (free-vars rator) (free-vars rand))))))
  ;bound-vars: Lc-Exp -> Scheme-List-of-Symbols
  (define bound-vars
    (lambda (lcexp)
      (cases lc-exp lcexp
        (var-exp (var) '())
        ;help: Scheme-List-Of-Symbols X Scheme-List-of-Symbols X Scheme-List-of-Symbols -> Scheme-List-Of-Symbols
        ;recursive helper function stores a list of all symbols to return in ret-val, iterates through list and returns ret-val when done
        (lambda-exp (bound-var body) (letrec ((help
                                               (lambda (bound-var vars ret-val)
                                                 (cond
                                                   ((null? vars) ret-val)
                                                   ((and (identifier? (car vars)) (in-body? (car vars) bound-var)) (help bound-var (cdr vars) (cons (car vars) ret-val)))
                                                   ((identifier? (car vars)) (help bound-var (cdr vars) ret-val))
                                                   (else (help bound-var (cdr vars) (append (help bound-var (car vars) '()) ret-val)))))))
                                       (help (append (get-binds body) bound-var) (get-vars body) '())))
        (app-exp (rator rand) (append (bound-vars rator) (bound-vars rand))))))
  ;get-binds gets list of all bound variables, including variables bound in nested lambdas
  ;get-binds: Lc-Exp -> Scheme-List-of-Symbols
  (define get-binds
    (lambda (lcexp)
      (cases lc-exp lcexp
        (var-exp (var) '())
        (lambda-exp (bound-var body) (append (get-binds body) bound-var))
        (app-exp (rator rand) (append (get-binds rator) (get-binds rand))))))
  ;get-binds returns a list of all symbols that occur in an lc-exp
  ;get-vars: Lc-Exp -> Scheme-List-of-Symbols
  (define get-vars
    (lambda (lcexp)
      (cases lc-exp lcexp
        (var-exp (var) (cond
                         ((eqv? 'nullouweghgeogghggouegggoigehogeoioghewoihewgohiweghoigejbnbfe var) '())
                         (else (list var))))
        (lambda-exp (bound-var body) (letrec ((help
                                               (lambda (vars ret-val)
                                                 (cond
                                                   ((null? vars) ret-val)
                                                   ((identifier? (car vars)) (help (cdr vars) (cons (car vars) ret-val)))
                                                   (else (help (cdr vars) (append (help (car vars) '()) ret-val)))))))
                                       (help (get-vars body) '())))
        (app-exp (rator rand) (append (get-vars rator) (get-vars rand))))))
  
  
  
  
  
  
  
  
  
  ;;;; DO NOT REMOVE THE FOLLOWING PARENTHESIS
  )
