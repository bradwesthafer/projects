/*   HOMEWORK 6     
     CMPSC 430    
     Brad Westhafer    */ 

declare
 studentname student.name%TYPE;
 curStudent student.name%TYPE;
 instructorname instructor.name%TYPE;
 cursor TakenCursor is
select distinct student.name, instructor.name
from student, instructor, teaches, takes
where student.id = takes.id and instructor.id = teaches.id and takes.course_id = teaches.course_id and takes.sec_id = teaches.sec_id and teaches.semester = takes.semester and takes.year = teaches.year
order by student.name, instructor.name;
begin
 open TakenCursor;
 dbms_output.put_line('   Student Name         Instructors Taken');
 dbms_output.put_line('-----------------------------------------------');
 curStudent := 'not a real name';
 loop
   fetch TakenCursor into studentname, instructorname;
   exit when TakenCursor%NOTFOUND;
   dbms_output.put('   ');
   if curStudent <> studentname then
   dbms_output.put(rpad(studentname, 20));
   dbms_output.put(' ');
   else
   dbms_output.put('                     ');
   end if;
   dbms_output.put(rpad(instructorname, 20));
   dbms_output.put(' ');
   dbms_output.new_line;
   curStudent := studentname;
 end loop;
 close TakenCursor;
end;
/
