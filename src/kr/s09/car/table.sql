create table car(
 num number primary key, --자동차관리번호(pk)
 name varchar2(30) not null, --이름
 cnumber varchar2(30) not null, --차번호 // number가 예약어라서 number라고 주면 오류남
 color varchar2(30) not null, --색상
 maker varchar2(30) not null, --제조사
 price number(10) not null, --가격
 reg_date date not null --자동차등록일 
);

create sequence car_seq;