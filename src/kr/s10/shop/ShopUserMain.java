package kr.s10.shop;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ShopUserMain {
	private BufferedReader br;
	private ShopDAO dao;
	
	public ShopUserMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new ShopDAO();
			callMenu();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null)try {br.close();}catch(IOException e) {};
		}
	}
	public void callMenu()throws IOException {
		while(true) {
			System.out.print("1.회원 가입,2.회원 정보,3.상품 구매,4.구매 내역,5.종료>");
			try {
				int no = Integer.parseInt(br.readLine());
				
				if(no==1) {//회원가입
					System.out.print("아이디:");
					String cust_id = br.readLine();
					System.out.print("이름:");
					String cust_name = br.readLine();
					System.out.print("주소:");
					String cust_address = br.readLine();
					System.out.print("전화번호:");
					String cust_tel = br.readLine();
					
					dao.insertCust(cust_id, cust_name, cust_address, cust_tel);
				}else if(no==2) {//정보 (id로 상세정보 출력)
					System.out.print("아이디:");
					String cust_id = br.readLine();
					
					dao.selectDetailCust(cust_id);
				}else if(no==3) {//상품구매
					//상품 보여주기
					dao.selectItem();

					System.out.println("상품 구매를 시작합니다.");
					System.out.println("----------------------------------------");
					System.out.print("상품 번호:");
					int item_num = Integer.parseInt(br.readLine());
					System.out.print("아이디:");
					String cust_id = br.readLine();
					
					dao.insertOrder(item_num, cust_id);
				}else if(no==4) {//구매내역 (id 명시해서 보기)
					System.out.print("아이디:");
					String cust_id = br.readLine();
					
					dao.selectOrderById(cust_id);
				}else if(no==5) {//종료
					System.out.println("프로그램을 종료합니다.");
					break;
				}else {
					System.out.println("잘못 입력했습니다.");
				}
			}catch(NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
	}
	
	public static void main(String[] args) {
		new ShopUserMain();
	}
}
/*
 * 회원가입
아이디:sky
이름:홍길동
주소:서울시
전화번호:010-1234-5678

아이디:dragon
이름:박문수
주소:부산시
전화번호:010-5678-1234
아이디:blue

이름:장영실
주소:대전시
전화번호:010-9876-5432
1개의 회원 정보를 저장했습니다.

아이디가 pk라서 동일한 아이디를 치면 무결성 오류가 남
========================


상품 구매를 시작합니다.
-------------------------------
상품 번호:4
아이디:sky
1개의 상품을 구매했습니다.

상품 번호:1
아이디:sky
1개의 상품을 구매했습니다.

상품 번호:3
아이디:blue
1개의 상품을 구매했습니다.

상품 번호:5
아이디:sky
1개의 상품을 구매했습니다.
1.회원 가입,2.회원 정보,3.상품 구매,4.구매 내역,5.종료>4
아이디:sky
========================

 */