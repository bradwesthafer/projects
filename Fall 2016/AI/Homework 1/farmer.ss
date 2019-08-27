					;CMPSC 441
					;Brad Westhafer
					;bdw5204@psu.edu
					;October 5, 2016
					;Homework 1

; load search algorithm
(load "search.ss")

; State format is (farmer (grain chicken fox)) with a 0 indicating the initial side of the river and a 1 indicating the other side of the river

; Define Initial State
(define init '((0 (0 0 0)) (S)))

; Define Goal State
(define goalState '(1 (1 1 1)))

; Define goal? to return true if all 4 are on the other side of the river and false otherwise
(define goal?
  (lambda (state)
    (equal? (car state) goalState)))

; Define fail? to return true if the chicken eats the grain or the fox eats the chicken
(define fail?
  (lambda (state)
    ;(printf "State: ~a" state)
    (cond
     ((equal? (car state) '(1 (0 0 1))) #t)
     ((equal? (car state) '(0 (1 1 0))) #t)
     ((equal? (car state) '(1 (1 0 0))) #t)
     ((equal? (car state) '(0 (0 1 1))) #t)
     (else #f))))

; In move-helper, current values are: 0 = empty, 1 = grain, 2 = chicken, 3 = fox
(define extend
  (lambda (state visited)
    (cond
     ((fail? state) '())
     (else
    (letrec
	((move-helper
	  (lambda (s v current output)
	    (cond
	     ((equal? current 0)
	      (cond
	       ((visited? (cons (shift (caar s)) (cdar s)) v) (move-helper s v (+ 1 current) output))
	       (else (move-helper s v (+ 1 current) (cons (list (cons (shift (caar s)) (cdar s)) (append '(E) (cadr s))) output)))))
	     ((equal? current 1)
	      (cond
	       ((not (equal? (caar s) (caadar s))) (move-helper s v (+ 1 current) output))
	       ((visited? (cons (shift (caar s)) (list (cons (shift (caadar s)) (cdadar s)))) v) (move-helper s v (+ 1 current) output))
	       (else (move-helper s v (+ 1 current) (cons (list (cons (shift (caar s)) (list (cons (shift (caadar s)) (cdadar s)))) (append '(G) (cadr s))) output)))))
	     ((equal? current 2)
	      (cond
	       ((not (equal? (caar s) (cadr (cadar s)))) (move-helper s v (+ 1 current) output))
	       ((visited? (cons (shift (caar s)) (list (list (caadar s) (shift (cadr (cadar s))) (caddr (cadar s))))) v) (move-helper s v (+ 1 current) output))
	       (else (move-helper s v (+ 1 current) (cons (list (cons (shift (caar s)) (list (list (caadar s) (shift (cadr (cadar s))) (caddr (cadar s))))) (append '(C) (cadr s))) output)))))
	     ((equal? current 3)
	      (cond
	       ((not (equal? (caar s) (caddr (cadar s)))) (move-helper s v (+ 1 current) output))
	       ((visited? (cons (shift (caar s)) (list (list (caadar s) (cadr (cadar s)) (shift (caddr (cadar s)))))) v) (move-helper s v (+ 1 current) output))
	       (else (move-helper s v (+ 1 current) (cons (list (cons (shift (caar s)) (list (list (caadar s) (cadr (cadar s)) (shift (caddr (cadar s)))))) (append '(F) (cadr s))) output)))))
	     (else output)))))
      (move-helper state visited 0 '()))))))

; visited? returns true if visited, false if not visited
(define visited?
  (lambda (state visited)
    (cond
      ((null? visited) #f)
      ((equal? state (caar visited)) #t)
      (else (visited? state (cdr visited))))))

; print calls printer, which prints the states to the goal in order from first to last
(define print
  (lambda (goal)
    (printer (cadadr goal) '())))

(define printer
  (lambda (input output)
    (cond
     ((null? input) (printf "===> To avoid losing his chicken or his grain, the farmer should make the following moves across the river in order (S = Start, E = Just Farmer, G = Grain, C = Chicken, F = Fox: ~a <===~%" output))
     (else (printer (cdr input) (cons (car input) output))))))

; shift takes a number. If the number is 0, it returns 1. Else, it returns 0.
(define shift
  (lambda (binary)
    (cond
     ((zero? binary) 1)
     (else 0))))

; The functions below call search.  Start search by calling either farmer-depth-first or farmer-breadth-first and passing init as the initial state.
(define farmer-depth-first-search
  (depth-first-search extend goal? print))

(define farmer-depth-first
  (lambda (init-state)
    (farmer-depth-first-search init-state)))


(define farmer-breadth-first-search
  (breadth-first-search extend goal? print))

(define farmer-breadth-first
  (lambda (init-state)
    (farmer-breadth-first-search init-state)))

; Shorthands for farmer-depth-first and farmer-breadth-first
(define fdf farmer-depth-first)
(define fbf farmer-breadth-first)
