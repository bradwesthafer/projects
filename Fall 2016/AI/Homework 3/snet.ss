					;CMPSC 441
					;Brad Westhafer
					;bdw5204@psu.edu
					;November 30, 2016
					;Homework 3

;; predicate calculus formulae for relationships:
;; all(x) isa(x, x)
;; all(x, y, z) isa(x, y)^isa(y,z)->isa(x,z)
;; all(x, y, z) has-part(x, y)^has-part(y, z)->has-part(x, z)
;; all(x, y, z) isa(x, y)^has-part(y, z)->has-part(x, z)
;; all(x, y, z) inst(x, y)^inst(y, z)->inst(x, z)
;; all(x, y, z) inst(x, y)^isa(y, z)->inst(x, z)
;; all(x, y, z) has-part(x, y)^inst(y, z)->has-part(x, z)
;; all(x, y, z) inst(x, y)^has-part(y, z)->has-part(x, z)
;; all(x, y, z) has-part(x, y)^isa(y, z)->has-part(x, z)



;;;
;;; Database maintaining the semantic net
;;; - procedures set-up-net and process-relation will populate *database*
;;;   in such a way that:
;;;
;;;      if we have relations
;;;
;;;           (isa helicopter air-vehicle)
;;;           (has-part helicopter propeller)
;;;           (has-part helicopter door)
;;;           (has-part propeller blade)
;;;
;;;      then *database* will look like
;;;
;;;           ((helicopter (isa (air-vehicle))          <-- from relation 1
;;;                          (has-part (propeller door))) <-- from relation 2, 3
;;;            (propeller (has-part (blade))))            <-- from relation 4
;;;
;;;
;;; You can load this file without any error.
;;;
;;; Loading this file will do the following
;;; - calls set-up-net with 'snet.data' as an argument
;;; - set-up-net reads snet.data line by line
;;; - for each line it reads, set-up-net calls process-relation
;;; - process-relation adds the line to the database
;;;



;;
;; Global database
;; - initially empty
;; - fills up with all of the relations in 'snet.data'
;; - queried by functions 'isa?' 'has-part?' and 'parts-of'
;;
(define *database* '())


;;
;; Build a semantic net
;; - The relations are given in a file named file-name
;; - This procedure reads a line at a time as a list from file-name
;;   and builds the database using the procedure process-relation
;; 
(define set-up-net
  (lambda (file-name)
    (with-input-from-file file-name
      (lambda ()
	(let loop ((in (read)))
	  (unless (eof-object? in)
	    (process-relation in)
	    (loop (read))))))))

;;
;; Updates *database* using set!
;; - calls function add-to-database, which tells it what to set *database* to
;; 
(define process-relation
  (lambda (rel)
    (set! *database* (add-to-database rel *database*))))

;;
;; Returns whatever the contents of *database* should be set! to
;; in order to add the relation 'rel' to the database.
;;
(define add-to-database
  (lambda (rel data)
    (cond
     ((null? data) (list (list (cadr rel) (list (car rel) (list (caddr rel))))))
     ((eq? (caar data) (cadr rel)) (letrec ((add-relationship
					     (lambda (obj)
					       (cond
						((null? obj) (list (list (car rel) (list (caddr rel)))))
						((eq? (caar obj) (car rel)) (list (list (caar obj) (append (cadar obj) (list (caddr rel))))))
						(else (cons (car obj) (add-relationship (cdr obj))))))))
				     (cons (append (list (caar data)) (add-relationship (cdar data))) (cdr data))))
     (else (cons (car data) (add-to-database rel (cdr data)))))))
    






;;;
;;; Procedures that implement semantic net 
;;;

;;
;; isa? returns '#t' (true) if 'x' isa 'y', '#f' otherwise.
;; Using the transitive property of the isa relationship, this
;; function recursively checks isa relationships until 'x' and 'y'
;; are the same (in which case, it returns '#t') or there are
;; no more relationships to check (in which case, it returns '#f')
;;
(define isa?
  (lambda (x y)
    (cond
     ((eq? x y) #T)
     (else (letrec ((call-on-database
		     (lambda (data)
		       (cond
			((null? data) #f)
			((eq? (caar data) x) (letrec ((find-isa
						       (lambda (d)
							 (cond
							  ((null? d) #f)
							  ((eq? (caar d) 'isa) (letrec ((call-isa
											(lambda (obj)
											  (cond
											   ((null? obj) #f)
											   (else (or (isa? (car obj) y) (call-isa (cdr obj))))))))
										(call-isa (cadar d))))
							  (else (find-isa (cdr d)))))))
					       (find-isa (cdar data))))
			(else (call-on-database (cdr data)))))))
	     (call-on-database *database*))))))
;;
;; has-part? calls the parts-of function on 'x', flattens that function's output
;; using the flatten method I wrote for Homework 0 then returns '#t' (true) if it
;; finds the part in the list or '#f' (false) otherwise. For instances, it calls
;; instance? to determine if 'x' is an instance and, if it is, it calls inst-has-part?,
;; which calls has-part? on whatever object 'x' is an instance of.
;;
(define has-part?
  (lambda (x y)
    (cond
     ((instance? x) (inst-has-part? x y))
     (else (letrec ((find-part
		     (lambda (parts)
		       (cond
			((null? parts) #f)
			((eq? (car parts) y) #t)
			(else (find-part (cdr parts)))))))
	     (find-part (flatten (parts-of x))))))))

;;
;; flatten is the same function I wrote for Homework 0, which is used by has-part? to
;; simplify that function.

(define flatten
  (lambda (slst)
    ;(printf "~a~n" slst)
    (cond
     ((null? slst) '())
     ((list? (car slst)) (flatten (up slst)))
     (else (cons (car slst) (flatten (cdr slst)))))))

;;
;; up is the same function I wrote for Homework 0, which is used by flatten.
;;

(define up
  (lambda (lst)
    ;(printf "~a~n" lst)
    (cond
     ((null? lst) '())
     ((eq? (list? (car lst)) #f) (cons (car lst) (up (cdr lst))))
     (else (append (car lst) (up (cdr lst)))))))

;;
;; instance? returns '#t' (true) if the 'x' passed to it is an instance
;; or '#f' (false) if it is not an instance.
;;

(define instance?
  (lambda (x)
    (letrec ((find-in-database
	      (lambda (data)
		(cond
		 ((null? data) #f)
		 ((eq? (caar data) x) (letrec ((find
						(lambda (d)
						  (cond
						   ((null? d) #f)
						   ((eq? (caar d) 'inst) #t)
						   (else (find (cdr d)))))))
					(find (cdar data))))
		 (else (find-in-database (cdr data)))))))
      (find-in-database *database*))))

;;
;; inst-has-part determines what 'x' is an instance of and calls has-part?
;; on whatever x is an instance of. If it is passed an 'x' that is not an
;; instance, it calls has-part? on 'x'.
;;

(define inst-has-part?
  (lambda (x y)
    (letrec ((find-in-database
	      (lambda (data)
		(cond
		 ((null? data) (has-part? x y))
		 ((eq? (caar data) x) (letrec ((find
						(lambda (d)
						  (cond
						   ((null? d) (has-part x y))
						   ((eq? (caar d) 'inst) (has-part? (caadar d) y))
						   (else (find (cdr d)))))))
					(find (cdar data))))
		 (else (find-in-database (cdr data)))))))
      (find-in-database *database*))))

;;
;; parts-of returns a list containing the parts-of 'x'.
;; If 'x' is an instance, it calls inst-parts-of on 'x',
;; which calls parts-of on whatever 'x' is an instance of.
;; Otherwise, it calls parts-of-helper on 'x', which returns
;; the parts of 'x'.
;;

(define parts-of
  (lambda (x)
    (cond
     ((instance? x) (inst-parts-of x))
     (else (parts-of-helper x '())))))

;;
;; inst-parts-of calls parts-of on whatever 'x' is an
;; instance of. If 'x' is not an instance, it calls
;; parts-of on 'x'.
;;

(define inst-parts-of
  (lambda (x)
    (letrec ((find-in-database
	      (lambda (data)
		(cond
		 ((null? data) (parts-of x))
		 ((eq? (caar data) x) (letrec ((find
						(lambda (d)
						  (cond
						   ((null? d) (parts-of x))
						   ((eq? (caar d) 'inst) (parts-of (caadar d)))
						   (else (find (cdr d)))))))
					(find (cdar data))))
		 (else (find-in-database (cdr data)))))))
      (find-in-database *database*))))

;;
;; parts-of-helper outputs the parts of 'x'.
;; The 'output' list is used to format the output.
;; It calls parts-of-isa to output the parts of
;; whatever 'x' isa.
;;

(define parts-of-helper
  (lambda (x output)
    (letrec ((find-in-database
	      (lambda (data out)
		(cond
		 ((null? data) (parts-of-isa x out))
		 ((eq? (caar data) x) (letrec ((find-isa
						(lambda (d outp)
						  (cond
						   ((null? d) (parts-of-isa x outp))
						   ((eq? (caar d) 'has-part) (letrec ((parts
										       (lambda (obj o)
											 (cond
											  ((null? obj) (parts-of-isa x o))
											  (else (parts (cdr obj) (append o (list (cons (car obj) (parts-of (car obj)))))))))))
									       (parts (cadar d) outp)))
						   (else (find-isa (cdr d) outp))))))
					(find-isa (cdar data) out)))
		 (else (find-in-database (cdr data) out))))))
      (find-in-database *database* output))))

;;
;; parts-of-isa outputs the parts of whatever 'x' isa.
;;

(define parts-of-isa
  (lambda (x output)
    (letrec ((find-in-database
	      (lambda (data out)
		(cond
		 ((null? data) out)
		 ((eq? (caar data) x) (letrec ((find-parts
						(lambda (d outp)
						  (cond
						   ((null? d) outp)
						   ((eq? (caar d) 'isa) (letrec ((parts
										       (lambda (obj o)
											 (cond
											  ((null? obj) o)
											  (else (parts (cdr obj) (parts-of-helper (car obj) o)))))))
									       (parts (cadar d) outp)))
						   (else (find-parts (cdr d) outp))))))
					(find-parts (cdar data) out)))
		 (else (find-in-database (cdr data) out))))))
      (find-in-database *database* output))))


;;;
;;; PLEASE DO NOT TOUCH BELOW THIS LINE
;;; IT IS FOR GRADING PURPOSE ONLY.
;;;
(set-up-net "snet.data")
