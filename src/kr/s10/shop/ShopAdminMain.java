package kr.s10.shop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ShopAdminMain {
	private BufferedReader br;
	private ShopDAO dao;
	
	public ShopAdminMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new ShopDAO();
			callMenu();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null) try {br.close();} catch(IOException e) {}
		}
	}
	
	public void callMenu()throws IOException {
		while(true) {
			System.out.print("1.상품 등록,2.상품 목록 보기,3.회원 목록,4.구매 목록,5.종료>");
			try {
				int no = Integer.parseInt(br.readLine());
				if(no==1) {//상품 등록
					System.out.print("이름:");
					String item_name = br.readLine();
					System.out.print("가격:");
					int item_price = Integer.parseInt(br.readLine());
					
					dao.insertItem(item_name, item_price);
				}else if(no==2) {//상품 목록 보기
					dao.selectItem();
				}else if(no==3) {//회원 목록 (전체 회원 목록)
					dao.selectCustomer();
				}else if(no==4) {//구매 목록 (모든 회원이 구매한 내용)
					dao.selectOrder();
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
		new ShopAdminMain();
	}
}
/*
insert> 건조기 / 냉장고 / 휴대폰 등록
번호	상품명	가격	등록일
5	자동차	90,000	2023-10-11
4	TV	60,000	2023-10-11
3	휴대폰	20,000	2023-10-11
2	냉장고	50,000	2023-10-11
1	건조기	10,000	2023-10-11



 */