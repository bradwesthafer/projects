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


;;;; Use the following test cases for initial testing.
;;;; Once you are done, remove the provided test cases
;;;;   and add at least 5 test cases of your own for
;;;;   each procedure


(module tests mzscheme
  
  (provide test-list)

  (define test-list
    '(

      ;; (duple n x)
      (bdw5204duple1 (equal?? (duple 5 5) (5 5 5 5 5)))
      (bdw5204duple2 (equal?? (duple 3 5) (5 5 5)))
      (bdw5204duple3 (equal?? (duple 4 5) (5 5 5 5)))
      (bdw5204duple4 (equal?? (duple 8 5) (5 5 5 5 5 5 5 5)))
      (bdw5204duple5 (equal?? (duple 9 5) (5 5 5 5 5 5 5 5 5)))

      ;; (invert lst)
      (bdw5204invert1 (equal?? (invert '((1 a) (2 a) (b 1) (b 2))) ((a 1) (a 2) (1 b) (2 b))))
      (bdw5204invert2 (equal?? (invert '((c d) (d c))) ((d c) (c d))))
      (bdw5204invert3 (equal?? (invert '((t v) (n e) (w s) (i s) (f a) (k e))) ((v t) (e n) (s w) (s i) (a f) (e k))))
      (bdw5204invert4 (equal?? (invert '((v e) (r y) (p o) (w e) (r f) (u l))) ((e v) (y r) (o p) (e w) (f r) (l u))))
      (bdw5204invert5 (equal?? (invert '((20 18) (35 75))) ((18 20) (75 35))))

      ;; (down lst)
      (bdw5204down1 (equal?? (down '(scheme is very powerful)) ((scheme) (is) (very) (powerful))))
      (bdw5204down2 (equal?? (down '(cout this)) ((cout) (this))))
      (bdw5204down3 (equal?? (down '(test case)) ((test) (case))))
      (bdw5204down4 (equal?? (down '(4)) ((4))))
      (bdw5204down5 (equal?? (down '((not) (my) (idea))) (((not)) ((my)) ((idea)))))

      ;; (up list)
      (bdw5204up1 (equal?? (up '((scheme) (is) (very) (powerful))) (scheme is very powerful)))
      (bdw5204up2 (equal?? (up '(test)) (test)))
      (bdw5204up3 (equal?? (up '((1))) (1)))
      (bdw5204up4 (equal?? (up '(42)) (42)))
      (bdw5204up5 (equal?? (up '(((scheme)))) ((scheme))))

      ;; (swapper s1 s2 slist)
      (bdw5204swapper1 (equal?? (swapper '(a b) '(a d) '((a b) (a d))) ((a d) (a b))))
      (bdw5204swapper2 (equal?? (swapper 'http 'https '(http turing cs hbg psu edu)) (https turing cs hbg psu edu)))
      (bdw5204swapper3 (equal?? (swapper 'racket 'scheme '(public static void main)) (public static void main)))
      (bdw5204swapper4 (equal?? (swapper 'powerful '(very powerful) '(scheme is powerful)) (scheme is (very powerful))))
      (bdw5204swapper5 (equal?? (swapper 5 15 '(5 10 15 20)) (15 10 5 20)))

      ;; (count-occurrences s slst)
      (bdw5204count-occurences1 (equal?? (count-occurrences 'scheme '(scheme is very powerful)) 1))
      (bdw5204count-occurences2 (equal?? (count-occurrences 'emacs '(emacs emacs emacs)) 3))
      (bdw5204count-occurences3 (equal?? (count-occurrences 'test '(this is a list)) 0))
      (bdw5204count-occurences4 (equal?? (count-occurrences 'harmful '(goto considered harmful)) 1))
      (bdw5204count-occurences5 (equal?? (count-occurrences 'gnome '(Ubuntu 18.04 LTS will use gnome instead of unity)) 1))

      ;; (product sos1 sos2)
      (bdw5204product1 (set-equal?? (product '(scheme emacs) '(very powerful)) ((scheme very) (scheme powerful) (emacs very) (emacs powerful))))
      (bdw5204product2 (set-equal?? (product '(1 2) '(a b c)) ((1 a) (1 b) (1 c) (2 a) (2 b) (2 c))))
      (bdw5204product3 (set-equal?? (product '(exception) '()) ()))
      (bdw5204product4 (set-equal?? (product '() '(Darth Vader)) ()))
      (bdw5204product5 (set-equal?? (product '(five) '(tests)) ((five tests))))

      ;; (filter-in pred lst)
      (bdw5204filter-in1 (equal?? (filter-in number? '(a b c)) ()))
      (bdw5204filter-in2 (equal?? (filter-in number? '(1 2 3)) (1 2 3)))
      (bdw5204filter-in3 (equal?? (filter-in number? '(a b c 1 2 3)) (1 2 3)))
      (bdw5204filter-in4 (equal?? (filter-in symbol? '(a b c)) (a b c)))
      (bdw5204filter-in5 (equal?? (filter-in symbol? '(1 2 3)) ()))

      ;; (list-index pred lst)
      (bdw5204list-index1 (equal?? (list-index symbol? '((scheme is very powerful))) #f))
      (bdw5204list-index2 (equal?? (list-index number? '((scheme is very powerful))) #f))
      (bdw5204list-index3 (equal?? (list-index list? '((scheme is very powerful))) 0))
      (bdw5204list-index4 (equal?? (list-index symbol? '(a b c)) 0))
      (bdw5204list-index5 (equal?? (list-index number? '(this is a list 42)) 4))

      ;; (every? pred lst)
      (bdw5204every?1 (equal?? (every? symbol? '((scheme is very powerful))) #f))
      (bdw5204every?2 (equal?? (every? number? '((scheme is very powerful))) #f))
      (bdw5204every?3 (equal?? (every? list? '((scheme is very powerful))) #t))
      (bdw5204every?4 (equal?? (every? symbol? '(scheme is very powerful)) #t))
      (bdw5204every?5 (equal?? (every? number? '(scheme is very powerful)) #f))

      ;; (exist? pred lst)
      (bdw5204exists?1 (equal?? (exists? symbol? '(scheme is very powerful)) #t))
      (bdw5204exists?2 (equal?? (exists? number? '(scheme is very powerful)) #f))
      (bdw5204exists?3 (equal?? (exists? list? '(scheme is very powerful)) #f))
      (bdw5204exists?4 (equal?? (exists? symbol? '(1 2 3 4 5 f)) #t))
      (bdw5204exists?5 (equal?? (exists? number? '(a b c d e f g)) #f))

      ;; (flatten slst)
      (bdw5204flatten1 (equal?? (flatten '(this is already flat)) (this is already flat)))
      (bdw5204flatten2 (equal?? (flatten '(virtual (reality) is the future)) (virtual reality is the future)))
      (bdw5204flatten3 (equal?? (flatten '((scheme) (is) (very) (powerful))) (scheme is very powerful)))
      (bdw5204flatten4 (equal?? (flatten '((monkeys wrote Windows operating system))) (monkeys wrote Windows operating system)))
      (bdw5204flatten5 (equal?? (flatten '(do not (cons) me)) (do not cons me)))

      ;; (merge lon1 lon2)
      (bdw5204merge1 (equal?? (merge '(1 2 3) '(4 5 6)) (1 2 3 4 5 6)))
      (bdw5204merge2 (equal?? (merge '(5 7 9) '(6 8 10)) (5 6 7 8 9 10)))
      (bdw5204merge3 (equal?? (merge '(66 77 88) '(1)) (1 66 77 88)))
      (bdw5204merge4 (equal?? (merge '(1492) '(1776)) (1492 1776)))
      (bdw5204merge5 (equal?? (merge '(1884 1892 1912 1932 1960 1976 1992 2008) '(1860 1888 1896 1920 1952 1968 1980 2000 2016))
                              (1860 1884 1888 1892 1896 1912 1920 1932 1952 1960 1968 1976 1980 1992 2000 2008 2016)))

      ;; (ribassoc s los v fail-value)
      (bdw5204ribassoc1 (equal?? (ribassoc 'scheme '(scheme is very powerful) #(emacs text editor powerful) 'weak) emacs))
      (bdw5204ribassoc2 (equal?? (ribassoc 'pidgeonhole '(pidgeonhole is very powerful) #(scheme c-sharp java lisp) 'dead) scheme))
      (bdw5204ribassoc3 (equal?? (ribassoc '5 '(5 10) #(five ten) 'zero) five))
      (bdw5204ribassoc4 (equal?? (ribassoc 'test '(when is the exam) #(test is next week) 'never) never))
      (bdw5204ribassoc5 (equal?? (ribassoc 'win '(you win) #(you lose) 'error) lose))
      
      ;; (rotate los)
      (bdw5204rotate1 (equal?? (rotate '(I wonder why)) (why I wonder)))
      (bdw5204rotate2 (equal?? (rotate '(squirrel)) (squirrel)))
      (bdw5204rotate3 (equal?? (rotate '(5 20 30)) (30 5 20)))
      (bdw5204rotate4 (equal?? (rotate '(bc ad)) (ad bc)))
      (bdw5204rotate5 (equal?? (rotate '(is very powerful Scheme)) (Scheme is very powerful)))

      )
    )
  )