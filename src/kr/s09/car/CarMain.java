package kr.s09.car;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class CarMain {
	private BufferedReader br;
	private CarDAO dao;
	
	public CarMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new CarDAO();
			callMenu();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			if(br!=null) try {br.close();} catch(IOException e) {}
		}
	}
	
	//메뉴
	public void callMenu()throws IOException {
		while(true) {
			System.out.print("1.자동차 정보 등록,2.목록 보기,3.자동차 상세 정보,4.정보 수정,5.정보 삭제,6.종료>");
			try {
				int no = Integer.parseInt(br.readLine());
				if(no == 1) {//자동차 정보 등록
					System.out.print("이름:");
					String name = br.readLine();
					System.out.print("자동차 번호:");
					String cnumber = br.readLine();
					System.out.print("색상:");
					String color = br.readLine();
					System.out.print("제조사:");
					String maker = br.readLine();
					System.out.print("가격:");
					int price = Integer.parseInt(br.readLine());
					
					dao.insertCar(name, cnumber, color, maker, price);
					
				}else if(no == 2) {//목록 보기
					dao.selectCar();
					
				}else if(no == 3) {//자동차 상세 정보
					//목록 보여주고 상세 정보
					dao.selectCar();
					
					System.out.print("선택한 자동차 관리 번호:");
					int num = Integer.parseInt(br.readLine());
					System.out.println("---------------------------------");
					dao.selectDetailCar(num);
					
				}else if(no == 4) {//정보 수정
					//목록 보기
					dao.selectCar();
					
					//바꿀 번호 입력
					System.out.print("수정할 자동차 정보의 관리 번호:");
					int num = Integer.parseInt(br.readLine());
					
					//상세글 보기
					dao.selectDetailCar(num);
					System.out.println("---------------------------------");
					
					System.out.print("이름:");
					String name = br.readLine();
					System.out.print("자동차 번호:");
					String cnumber = br.readLine();
					System.out.print("색상:");
					String color = br.readLine();
					System.out.print("제조사:");
					String maker = br.readLine();
					System.out.print("가격:");
					int price = Integer.parseInt(br.readLine());
					
					dao.updateCar(num, name, cnumber, color, maker, price);
					
				}else if(no == 5) {//정보 삭제
					//목록보기
					dao.selectCar();
					
					//삭제하고 싶은 글 번호 입력
					System.out.print("삭제할 정보의 관리 번호:");
					int num = Integer.parseInt(br.readLine());
					dao.deleteCar(num);
					
				}else if(no == 6) {//종료
					System.out.println("프로그램 종료");
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
		new CarMain(); //생성자 호출
	}
}
/*
 * 1번 해서 insert하기
 * 소나타/서울 2/검정/현대/2000
 * 그랜저/부산 4/흰색/현대/3000
 * 아이오닉/대전 5/은색/현대/5000
 * =========================
 * 4번 update
 * 이름:아이오닉5
 * 자동차 번호:대전 5
 * 색상:금색
 * 제조사:현대
 * 가격:5000
 * 1개의 행을 수정했습니다.
 * 
 * 수정하기
 * =========================
 * 5.삭제하기 (2번 삭제 했음)
1.자동차 정보 등록,2.목록 보기,3.자동차 상세 정보,4.정보 수정,5.정보 삭제,6.종료>5
---------------------------------
번호	이름	제조사	등록일
3	아이오닉5	현대	2023-10-10
2	그랜저	현대	2023-10-10
1	소나타	현대	2023-10-10
---------------------------------
삭제할 정보의 관리 번호:2
1개의 행을 삭제했습니다.
1.자동차 정보 등록,2.목록 보기,3.자동차 상세 정보,4.정보 수정,5.정보 삭제,6.종료>2
---------------------------------
번호	이름	제조사	등록일
3	아이오닉5	현대	2023-10-10
1	소나타	현대	2023-10-10
---------------------------------

====> 2번 삭제 했음

 * 
 */
