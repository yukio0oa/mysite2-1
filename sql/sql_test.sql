-- user sql test

insert
  into users
values ( seq_users.nextval, '안대혁', 'kickscar@gmail.com', '1234', 'male');

rollback;

select no, name, email 
  from users
 where email='kickscar@gmail.com'
   and password = '1234';

select * from users;
   
-- board

-- 새글 쓰기
insert
  into board
values (seq_board.nextval, 
        '안녕6',
		'...',
		sysdate,
		0, 
		nvl((select max(g_no) from board), 0) + 1, 
		1, 
		0, 
		2); 

commit;
rollback;
-- list
select *
  from ( select rownum rn, c.*
           from ( select a.no, 
                         a.title, 
	                     a.hit, 
	                     to_char(a.reg_date, 'yyyy-mm-dd'),
	                     b.name as user_name,
	           
			             b.no as users_no  
                    from board a,
                         users b
                   where a.users_no = b.no
                  order by a.g_no desc, a.o_no asc) c )
  where (1-1)*5 + 1 <= rn and rn <= 1*5				  
		  
		    	   
--   	   
--  
--  where 6 <= rownum and rownum <= 10
   	   


 
     