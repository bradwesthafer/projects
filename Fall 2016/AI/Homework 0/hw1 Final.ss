					;CMPSC 441
					;Brad Westhafer
					;bdw5204@psu.edu
					;September 12, 2016
					;Homework 0

(define duple
  (lambda (n x)
    (cond
     ((zero? n) '())
     ((eq? n 1) (cons x '()))
     (else (cons x (duple (sub1 n) x))))))

(define invert
  (lambda (lst)
    (cond
     ((null? lst) '())
     ((null? (cdr lst)) (cons (cons (car (cdr (car lst))) (cons (car (car lst)) '())) '()))
     (else (cons (cons (car (cdr (car lst))) (cons (car (car lst)) '())) (invert (cdr lst)))))))

(define list-index
  (lambda (s los)
    (letrec ((index
	     (lambda (s2 lo x)
	       (cond
		((null? lo) -1)
		((eq? s2 (car lo)) x)
		(else (index s2 (cdr lo) (add1 x)))))))
      (index s los 0))))

(define vector-index
  (lambda (s vos)
    (letrec ((index
	      (lambda (s2 vo x)
		(cond
		 ((= (vector-length vo) x) -1)
		 ((eq? (vector-ref vo x) s2) x)
		 (else (index s2 vo (add1 x)))))))
	     (index s vos 0))))

(define ribassoc
  (lambda (s los v fail-value)
    (cond
     ((eq? (list-index s los) -1) fail-value)
     (else (vector-ref v (list-index s los))))))

(define filter-in
  (lambda (p lst)
    (cond
     ((null? lst) '())
     ((p (car lst)) (cons (car lst) (filter-in p (cdr lst))))
     (else (filter-in p (cdr lst))))))

(define product
  (lambda (los1 los2)
    (letrec ((help
	      (lambda (lst itm)
	      (cond
	       ((null? lst) '())
	       (else (append (cons (cons itm (cons (car lst) '())) '()) (help (cdr lst) itm)))))))
      (cond
       ((null? los1) '())
       (else (append (help los2 (car los1)) (product (cdr los1) los2)))))))

(define swapper
  (lambda (s1 s2 slst)
    (cond
     ((null? slst) '())
     ((list? (car slst)) (cons (swapper s1 s2 (car slst)) (swapper s1 s2 (cdr slst))))
     ((eq? (car slst) s1) (cons s2 (swapper s1 s2 (cdr slst))))
     ((eq? (car slst) s2) (cons s1 (swapper s1 s2 (cdr slst))))
     (else (cons (car slst) (swapper s1 s2 (cdr slst)))))))

(define rotate
  (lambda (los)
    (letrec ((help
	      (lambda (lst result)
		(cond
		 ((null? lst) '())
		 ((null? (cdr lst)) (cons (car lst) result))
		 (else (help (cdr lst) (append result (cons (car lst) '()))))))))
      (help los '()))))

(define down
  (lambda (lst)
    (cond
     ((null? lst) '())
     (else (cons (cons (car lst) '()) (down (cdr lst)))))))

(define up
  (lambda (lst)
    (cond
     ((null? lst) '())
     ((eq? (list? (car lst)) #f) (cons (car lst) (up (cdr lst))))
     (else (append (car lst) (up (cdr lst)))))))

(define count-occurrences
  (lambda (s slst)
    (cond
     ((null? slst) 0)
     ((list? (car slst)) (+ (count-occurrences s (car slst)) (count-occurrences s (cdr slst))))
     ((eq? (car slst) s) (+ 1 (count-occurrences s (cdr slst))))
     (else (+ 0 (count-occurrences s (cdr slst)))))))

(define flatten
  (lambda (slst)
    (cond
     ((null? slst) '())
     ((list? (car slst)) (flatten (up slst)))
     (else (cons (car slst) (flatten (cdr slst)))))))

(define merge
  (lambda (lon1 lon2)
    (cond
     ((null? lon1) lon2)
     ((null? lon2) lon1)
     ((< (car lon1) (car lon2)) (cons (car lon1) (merge (cdr lon1) lon2)))
     (else (cons (car lon2) (merge lon1 (cdr lon2)))))))

(define path
  (lambda (n bst)
    (letrec ((help
	(lambda (n bst p)
	  (cond
	   ((null? bst) 'fail)
	   ((null? (car bst)) 'fail)
	   ((> (car bst) n) (help n (car (cdr bst)) (flatten (cons p '(L)))))
	   ((< (car bst) n) (help n (car (cdr (cdr bst))) (flatten (cons p '(R)))))
	   (else p)))))
      (help n bst '()))))

(define car&cdr
  (lambda (s slst errvalue)
    (cond
     ((null? slst) errvalue)
     ((eq? s (car slst)) (list 'lambda '(lst) '(car lst)))
     (else
      (letrec ((help
	      (lambda (s slst errvalue funct)
		(cond
		 ((null? slst) errvalue)
		 ((eq? s (car slst)) (list 'lambda '(lst) (list 'car funct)))
		 (else (help s (cdr slst) errvalue (list 'cdr funct)))))))
      (help s (cdr slst) errvalue '(cdr lst)))))))

(define car&cdr2
  (lambda (s slst errvalue)
    (cond
     ((null? slst) errvalue)
     ((eq? s (car slst)) 'car)
     (else
      (letrec ((help
		(lambda (s slst errvalue cdrcount extra)
		  (cond
		   ((null? slst) errvalue)
		   ((eq? s (car slst)) (cond
					((null? extra) (list 'compose 'car (cons 'compose (duple cdrcount 'cdr))))
					((eq? cdrcount 0) (list 'compose 'car (up extra)))
					(else (list 'compose 'car (append '(compose) (duple cdrcount 'cdr) extra)))))
		   ((list? (car slst)) (cond
					((eq? (help s (car slst) errvalue 0 extra) errvalue) (help s (cdr slst) errvalue (add1 cdrcount) extra))
					(else (append (help s (car slst) errvalue 0 (list (list 'compose 'car (append '(compose) (duple cdrcount 'cdr)))))))))
		   (else (help s (cdr slst) errvalue (add1 cdrcount) extra))))))
	(help s slst errvalue 0 '()))))))

(define (compose p1 . p2)
    (lambda (x)
      (cond
       ((null? p1) x)
       ((null? p2) (p1 x))
       (else (p1 ((apply compose p2) x))))))

(define sort1
  (lambda (lon)
    (cond
     ((null? lon) '())
     ((null? (cdr lon)) (cons (car lon) '()))
     (else (merge (cons (car lon) '()) (sort1 (cdr lon)))))))

(define sort2
  (lambda (predicate lon)
    (cond
     ((null? lon) '())
     ((eq? predicate <) (sort1 lon))
     (else
      (letrec ((help
		     (lambda (lst)
		       (cond
			((null? lst) '())
			((null? (cdr lst)) (cons (car lst) '()))
			(else (mergebackwards (cons (car lst) '()) (help (cdr lst))))))))
	     (up (help lon)))))))

(define mergebackwards
  (lambda (lon1 lon2)
    (cond
     ((null? lon1) lon2)
     ((null? lon2) lon1)
     ((> (car lon1) (car lon2)) (cons (car lon1) (mergebackwards (cdr lon1) lon2)))
     (else (cons (car lon2) (mergebackwards lon1 (cdr lon2)))))))
