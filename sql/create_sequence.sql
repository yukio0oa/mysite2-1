-- create sequence

-- seq_users
drop sequence seq_users;

create sequence seq_users
start with 1
increment by 1
maxvalue 9999999999;

-- seq_board
drop sequence seq_board;


create sequence seq_board
start with 1
increment by 1
maxvalue 9999999999;

-- seq_gallery
drop sequence seq_gallery;

create sequence seq_gallery
start with 1
increment by 1
maxValue 9999999999;
