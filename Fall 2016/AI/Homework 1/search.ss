					;CMPSC 441
					;Brad Westhafer
					;bdw5204@psu.edu
					;October 5, 2016
					;Homework 1
(define search
 (lambda (merge-queue extend goal? print-path init-state)
   (letrec
       ((search-helper
          (lambda (queue visited)
            (cond
              ((null? queue) #f)
              ((goal? (caar queue))
               (begin
                 (print-path (car queue))
                 (car queue)))
              (else
                (let ((successors (extend (caar queue) visited)))
                  (cond
                    ((null? successors)
                     (search-helper (cdr queue) visited))
                    (else
                      (let ((new-paths (extend-path successors (car queue))))
                        (search-helper
                          (merge-queue queue new-paths)
                          (append successors visited)))))))))))
     (search-helper
       (list (list init-state))   ; initial queue
       (list init-state)))))      ; initial visited

; Curry the generic search algorithm

(define curriedsearch
  (lambda (merge-queue)
     (lambda (e g p)
       (letrec
	  ((problem-specific
	    (lambda (extend goal? print-path)
	      (lambda (i)
	      (letrec
		  ((instance-specific
		    (lambda (init-state)
		      (search merge-queue extend goal? print-path init-state))))
		(instance-specific i))))))
	(problem-specific e g p)))))

(define extend-path
  (lambda (successors path)
    (if (null? successors)
        '()
        (cons (cons (car successors) path)
          (extend-path (cdr successors) path)))))


;; merge new extended paths to queue for depth first search

(define depth-first-merge
  (lambda (queue paths)
    (append paths queue)))


;; merge new extended paths to queue for breadth first search

(define breadth-first-merge
  (lambda (queue paths)
    (append queue paths)))

;; customize the generic search for depth first search

(define depth-first-search
  (lambda (extend goal? print-path)
    ((curriedsearch depth-first-merge) extend goal? print-path)))

;; customize the generic search for breadth first search

(define breadth-first-search
  (lambda (extend goal? print-path)
    ((curriedsearch breadth-first-merge) extend goal? print-path)))
