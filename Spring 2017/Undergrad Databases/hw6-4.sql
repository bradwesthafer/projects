/*   HOMEWORK 6     
     CMPSC 430    
     Brad Westhafer    */ 

declare
 major student.dept_name%TYPE;
 lastMajor student.dept_name%TYPE;
 name student.name%TYPE;
 credits student.tot_cred%TYPE;
 cursor MajCursor is
  select dept_name, name, tot_cred
  from student
  order by dept_name, name, tot_cred;
begin
 open MajCursor;
 dbms_output.put_line('   Major                Student Name         Total Credits');
 dbms_output.put_line('----------------------------------------------------------');
 lastMajor := 'not a major';
 loop
   fetch MajCursor into major, name, credits;
   exit when MajCursor%NOTFOUND;
   dbms_output.put('   ');
   if lastMajor <> major then
   dbms_output.put(rpad(major, 20));
   dbms_output.put(' ');
   else
   dbms_output.put('                     ');
   end if;
   dbms_output.put(rpad(name, 20));
   dbms_output.put(' ');
   dbms_output.put(lpad(credits, 8));
   dbms_output.new_line;
   lastMajor := major;
 end loop;
 close MajCursor;
end;
/
