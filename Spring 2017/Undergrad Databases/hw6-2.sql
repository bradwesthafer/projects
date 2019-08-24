/*   HOMEWORK 6     
     CMPSC 430    
     Brad Westhafer    */ 

declare
 dept instructor.dept_name%TYPE;
 avgsal instructor.salary%TYPE;
 cursor SalCursor is
  select dept_name, avg(salary)
  from instructor
  group by dept_name;
begin
 open SalCursor;
 dbms_output.put_line('   Department           Salary');
 dbms_output.put_line('----------------------------------');
 loop
   fetch SalCursor into dept, avgsal;
   exit when SalCursor%NOTFOUND;
   dbms_output.put('   ');
   dbms_output.put(rpad(dept, 20));
   dbms_output.put(' ');
   dbms_output.put(rpad(avgsal, 10));
   dbms_output.new_line;
 end loop;
 close SalCursor;
end;
/
