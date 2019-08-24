;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; CMPSC 460-001                        ;;;
;;; Homework 1                           ;;;
;;; Brad Westhafer                       ;;;
;;; bdw5204                              ;;;
;;;                                      ;;;
;;; Note:                                ;;;
;;;                                      ;;;
;;;                                      ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(module hw1 (lib "eopl.ss" "eopl")

  (provide
   duple
   invert
   down
   up
   swapper
   count-occurrences
   product
   filter-in
   list-index
   every?
   exists?
   flatten
   merge
   rotate
   ribassoc
   )


  ;;;; Define each of your procedures
  ;;;;   the corresponding comments
  
  
  ;; (duple n x)
  ;duple : Int x exp -> (exps)
  (define duple
    (lambda (n x)
      (cond
        ((zero? n) '())
        (else (cons x (duple (- n 1) x))))))
  ;; (invert lst)
  ;invert : ((exp exp)*) -> ((exp exp)*)
  (define invert
    (lambda (lst)
      (cond
        ((null? lst) '())
        (else (cons (cons (car (cdr (car lst))) (cons (car (car lst)) '())) (invert (cdr lst)))))))
  ;; (down lst)
  ;down : (exp*) -> (exp*)
  (define down
    (lambda (lst)
      (cond
        ((null? lst) '())
        (else (cons (cons (car lst) '()) (down (cdr lst)))))))
  ;; (up lst)
  ;up : (exp*) -> (exp*)
  (define up
    (lambda (lst)
      (cond
      ((null? lst) '())
      ((null? (car lst)) (up (cdr lst)))
      ((list? (car lst)) (cons (caar lst) (up (cons (cdar lst) (cdr lst)))))
      (else (cons (car lst) (up (cdr lst)))))))
  ;; (swapper s1 s2 slst)
  ;swapper exp x exp x (exp*) -> (exp*)
  (define swapper
    (lambda (s1 s2 slst)
    (cond
      ((null? slst) '())
      ((equal? s1 (car slst)) (cons s2 (swapper s1 s2 (cdr slst))))
      ((equal? s2 (car slst)) (cons s1 (swapper s1 s2 (cdr slst))))
      ((list? (car slst)) (cons (swapper s1 s2 (car slst)) (swapper s1 s2 (cdr slst))))
      ((eq? (car slst) s1) (cons s2 (swapper s1 s2 (cdr slst))))
      ((eq? (car slst) s2) (cons s1 (swapper s1 s2 (cdr slst))))
      (else (cons (car slst) (swapper s1 s2 (cdr slst)))))))
  ;; (count-occurrences s slst)
  ;count-occurrences : exp x (exp*) -> Int
  (define count-occurrences
    (lambda (s slst)
      (cond
        ((null? slst) 0)
        ((equal? s (car slst)) (+ 1 (count-occurrences s (cdr slst))))
        ((list? (car slst)) (+ (count-occurrences s (car slst)) (count-occurrences s (cdr slst))))
        ((eq? (car slst) s) (+ 1 (count-occurrences s (cdr slst))))
        (else (count-occurrences s (cdr slst))))))
  ;; (product sos1 sos2)
  ;product : (Exp*) x (Exp*) -> ((Exp Exp)*)
  (define product
    (lambda (sos1 sos2)
      (cond
        ((null? sos1) '())
        ((null? sos2) '())
        ;help : (Exp*) -> ((Exp Exp)*)
        (else (letrec ((help
                        (lambda (sos2cur)
                          (cond
                            ((null? sos2cur) (product (cdr sos1) sos2))
                            (else (cons (cons (car sos1) (cons (car sos2cur) '())) (help (cdr sos2cur))))))))
                (help sos2))))))
  ;; (filter-in pred lst)
  ;filter-in : procedure x (Exp*) -> (Exp*)
  (define filter-in
    (lambda (pred lst)
      (cond
        ((null? lst) '())
        ((pred (car lst)) (cons (car lst) (filter-in pred (cdr lst))))
        (else (filter-in pred (cdr lst))))))
  ;; (list-index pred lst)
  ;list-index : procedure x (Exp*) -> Int | Boolean
  (define list-index
      (lambda (pred lst)
        ;help : (Exp*) x Int -> Int | Boolean
        (letrec ((help
                  (lambda (lst total)
                    (cond
                      ((null? lst) #f)
                      ((pred (car lst)) total)
                      (else (help (cdr lst) (+ 1 total))))))) (help lst 0))))
  ;; (every? pred lst)
  ;every? -> procedure x (Exp*) -> Boolean
  (define every?
    (lambda (pred lst)
      (cond
        ((null? lst) #t)
        ((pred (car lst)) (every? pred (cdr lst)))
        (else #f))))
  ;; (exists? pred lst)
  ;exists? : procedure x (Exp*) -> Boolean
  (define exists?
    (lambda (pred lst)
      (cond
        ((null? lst) #f)
        ((pred (car lst)) #t)
        (else (exists? pred (cdr lst))))))
  ;; (flatten slst)
  ;flatten: (Exp*) -> (Exp*)
  (define flatten
    (lambda (slst)
      ;help: (Exp*) x (Exp*) x (Exp*) -> (Exp*)
      (letrec ((help
              ;retVal stores the return list in reverse order
              ;save stores the cdr temporarily while traversing car
                (lambda (slst retVal save)
                  (cond
                    ((and (null? slst) (null? save) (reverse retVal)))
                    ((null? slst) (help save retVal '()))
                    ((symbol? (car slst)) (help (cdr slst) (cons (car slst) retVal) save))
                    ((null? (car slst)) (help (cdr slst) retVal save))
                    ((null? (cdr slst)) (help (car slst) retVal save))
                    ((null? save) (help (car slst) retVal (cdr slst)))
                    (else (help (car slst) retVal (cons (cdr slst) save)))))))(help slst '() '()))))
  ;; (merge lon1 lon2)
  ;merge: (Exp*) x (Exp*) -> (Exp*)
  (define merge
    (lambda (lon1 lon2)
      (cond
        ((null? lon1) lon2)
        ((null? lon2) lon1)
        ((< (car lon1) (car lon2)) (cons (car lon1) (merge (cdr lon1) lon2)))
        (else (cons (car lon2) (merge lon1 (cdr lon2)))))))
  ;; (ribassoc s los v fail-value)
  ;ribassoc: Exp (Exp*) Vector Exp -> Exp
  (define ribassoc
    (lambda (s los v fail-value)
      (cond
        ((null? los) fail-value)
        ((eq? (car los) s) (car (vector->list v)))
        (else (ribassoc s (cdr los) (list->vector (cdr (vector->list v))) fail-value)))))
  ;; (rotate los)
  ;rotate: (Exp*) -> (Exp*)
  (define rotate
    (lambda (los)
      ;help: (Exp*) x (Exp*) -> (Exp*)
      (letrec ((help
                (lambda (los retVal)
                  (cond
                    ((null? los) retVal)
                    ((null? (cdr los)) (cons (car los) (reverse retVal)))
                    (else (help (cdr los) (cons (car los) retVal)))))))(help los '()))))
                          

  
             
  

  ;;;; DO NOT REMOVE THE FOLLOWING PARENTHESIS
)
