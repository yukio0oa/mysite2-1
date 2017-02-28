select * 
  from ( select rownum as rn, a.*
           from (  select no,
                          name,
	                      content,
	                      to_char(reg_date, 'yyyy-mm-dd') as regDate 
                     from guestbook
                 order by no desc ) a )
  where (2-1)*5 + 1  <= rn and rn <= 2*5; 		  