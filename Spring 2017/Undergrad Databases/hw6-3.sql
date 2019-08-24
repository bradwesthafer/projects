/*   HOMEWORK 6     
     CMPSC 430    
     Brad Westhafer    */ 

create or replace trigger trig_min_avgsal
 after insert or update or delete on instructor
 declare
  dept instructor.dept_name%TYPE;
  avgsal instructor.salary%TYPE;
  cursor SalCursor is
   select dept_name, avg(salary)
   from instructor
   group by dept_name;
 begin
  open SalCursor;
  loop
   fetch SalCursor into dept, avgsal;
   exit when SalCursor%NOTFOUND;
   if avgsal < 40000 then
    RAISE_APPLICATION_ERROR(-20000, dept);
   end if;
  end loop;
  close SalCursor;
end trig_min_avgsal;
/
