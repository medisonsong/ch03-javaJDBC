create table test2(
 num number primary key,
 title varchar2(30) not ull,
 name varchar2(30) not null,
 memo varchar2(4000) not null,
 email varchar2(30),
 reg_date date not null
);
create sequence test2_seq;