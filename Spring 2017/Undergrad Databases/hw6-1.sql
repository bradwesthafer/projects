/*   HOMEWORK 6     
     CMPSC 430    
     Brad Westhafer    */ 

CREATE OR REPLACE TRIGGER trig_update_student_id
 before update on student
 for each row
 begin
 update takes
 set id = :new.id
 where id = :old.id;
-- update advisor
--   set s_id = :new.id
--   where s_id = :old.id;
 end trig_update_student_id;
/

/* If the student has an advisor, the update fails because
   it violates the foreign key constraint for the advisor relation.
   This can be fixed  by using the commented out code contained
   within my trigger.
*/
