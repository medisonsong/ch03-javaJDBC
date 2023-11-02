create table sitem(
 item_num number primary key, --상품번호
 item_name varchar2(30) not null, --상품명
 item_price number(9) not null, --가격
 item_date date default sysdate not null --등록날짜 
 --insert할 때 날짜를 입력하지 않으면 default날짜(sysdate)가 입력되는거 (sysdate라고 java class 내에서 따로 명시안해도됨)
);
create sequence sitem_seq;

create table customer( --<회원테이블>
 cust_id varchar2(30) primary key, --회원아이디
 cust_name varchar2(30) not null, --이름
 cust_address varchar2(90) not null, --주소
 cust_tel varchar2(20) not null, --전화번호
 cust_date date default sysdate not null --가입일
);

create table sorder( --상품을 여러 개 한번에 구매할 수 없고 한 번에 하나만 구매 가능
 order_num number primary key, --주문번호
 cust_id varchar2(30) references customer(cust_id), --주문자아이디
 item_num number references sitem(item_num), --주문상품번호
 order_date date default sysdate not null --주문날짜
);
create sequence sorder_seq;