//관리자 UI

package kr.s11.book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BookAdminMain {
	private BufferedReader br;
	private BookDAO dao;
	
	public BookAdminMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new BookDAO();
			callMenu();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br!=null) try {br.close();} catch(IOException e) {}
		}
	}
	
	public void callMenu()throws IOException {
		while(true) {
			System.out.print("1.도서 등록,2.도서 목록,3.대출 목록,4.회원 목록,5.종료>");
			try {
				int no = Integer.parseInt(br.readLine());
				
				if(no == 1) {//도서등록
					/*
					 * 도서명:자바
					 * 카테고리:IT
					 * 1건의 도서 정보를 등록했습니다.
					 */
					System.out.print("도서명:");
					String bk_name = br.readLine();
					System.out.print("카테고리:");
					String bk_category = br.readLine();
					
					dao.insertBook(bk_name, bk_category);
				}else if(no == 2) {//도서목록
					/*
					 * ---------------------------------
					 * 번호 카테고리  도서명  대출여부  등록일
					 * 42   천문   별이야기  대출가능  2023-10-10
					 * 41   IT    자바     대출중   2023-10-10
					 * ---------------------------------
					 * 
					 * 대출여부> reservation 이랑 조인해서 뿌리면 됨
					 */
										
					dao.selectBook();
				}else if(no == 3) {//전체대출목록
					/*
					 * 계속 이력이 쌓이는 형태
					 * reservation data를 뿌려주면 됨
					 * 반납일 > update하면됨
					 * 번호> reservation 번호
					 * ---------------------------------------------------
					 * 번호   대출여부    대출자id    대출도서명    대출일    반납일
					 * ----------------------------------------------------
					 * 20    대출       blue        자바   2023-10-10 
					 * 19    반납       sky       별이야기 2023-10-09 2023-10-10
					 * ----------------------------------------------------
					 */
					dao.rentBookList();
				}else if(no == 4) {//회원 목록
					/*
					 * -----------------------------------------
					 * id     이름      전화번호           가입일 
					 * -----------------------------------------
					 * sky    홍길동   010-1234-5678  2023-09-09
					 * blue   박영식   010-5678-1234  2023-09-08
					 * -----------------------------------------
					 */
					dao.selectMember();
				}else if(no == 5) {//종료
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
		new BookAdminMain();
	}
}
