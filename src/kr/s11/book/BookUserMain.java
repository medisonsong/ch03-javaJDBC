//회원 UI
//회원 로그인 처리 메뉴가 새로 생김

package kr.s11.book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class BookUserMain {
	private BufferedReader br;
	private BookDAO dao;
	private String me_id; // 로그인 되면 아이디를 저장하기 위해서 (로그인 처리를 위해서 me_id, flag 생성)
	private boolean flag; //boolean 타입으로 로그인 성공 여부 저장 (객체 생성되면 초기값이 false임)
	
	public BookUserMain() {
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
	
	public void callMenu()throws IOException{
		while(true) { //flag = false면 여기로 빠져서 다음 메뉴 못 들어가게 함
			System.out.print("1.로그인,2.회원 가입,3.종료>");
			try {
				int no = Integer.parseInt(br.readLine());
				
				if(no==1) { //로그인 처리
					System.out.print("아이디:");
					me_id = br.readLine(); //위에 이미 String me_id 선언했기 때문에 또 선언할 필요 x
					System.out.print("비밀번호:");
					String me_passwd = br.readLine();
					
					flag = dao.loginCheck(me_id, me_passwd);
					if(flag) {//flag =true
						System.out.println(me_id + "님 로그인 되었습니다.");
						break; // 빠져나가서 밑에 있는 작업을 할 수 있게 만듦
					}
					System.out.println("아이디 또는 비밀번호 불일치");
					
				}else if(no==2) { //회원 가입 (회원 가입이 되어 있어야지만 로그인이 됨) //+아이디 중복체크
					System.out.print("아이디:");
					String me_id = br.readLine();
					
					//아이디 중복체크
					int check = dao.checkId(me_id);
					if(check == 1) {
						System.out.println("아이디가 중복되었습니다.");
					}else {
						//여기서 회원가입 작업하면 됨.
						/*
						 * 비밀번호:1234
						 * 이름:홍길동
						 * 전화번호:010-1234-5678
						 * 회원 가입이 완료되었습니다.
						 */
						System.out.print("비밀번호:");
						String me_passwd = br.readLine();
						System.out.print("이름:");
						String me_name = br.readLine();
						System.out.print("전화번호:");
						String me_phone = br.readLine();
						dao.insertMember(me_id, me_passwd, me_name, me_phone);
					}
				}else if(no==3) { //종료
					System.out.println("프로그램 종료");
					break;
				}else { 
					System.out.println("잘못 입력했습니다.");
				}
			}catch(NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
		while(flag) { //flag = true (로그인=true)가 되면 여기로 들어와서 다음 메뉴 실행
			System.out.print("1.도서대출,2.MY대출목록,3.대출도서 반납,4.종료>"); // 2번 할때 또 id 입력할 필요 없이 선언한 me_id 사용하면됨
			try {
				int no = Integer.parseInt(br.readLine());
				if(no==1) {//도서대출
					/*
					 * 먼저 도서 목록 보여주고
					 * ------------------------------------
					 * 번호   카테고리   도서명   대출여부   등록일
					 * ------------------------------------
					 * 43     IT      자바     대출가능   2023-10-10
					 * 42     천문   별이야기    대출가능   2023-10-10
					 * ------------------------------------
					 * 
					 * [도서 대출하기] // reservation에 넣으면 될듯
					 * 도서 번호:43
					 * 도서 1건이 대출되었습니다.
					 */
					dao.selectBook();
					
					System.out.println("[도서 대출하기]");
					System.out.print("도서 번호:");
					int bk_num = Integer.parseInt(br.readLine());
					int check = dao.checkBook(bk_num);
					if(check==1) {
						System.out.println("현재 도서가 대출 중입니다.");
					}else {
						//여기서 대출 하면됨
						int re_status = 1;
						dao.rentBook(re_status, bk_num, me_id);
					}
					
				}else if(no==2) {//my대출목록
					/*
					 * me_id 이용해서 입력 없이 그냥 바로 읽어오면 됨
					 * ------------------------------------
					 * 번호   도서명   대출여부    등록일
					 * ------------------------------------
					 * 41     자바    대출중     2023-10-10
					 * ------------------------------------
					 */
					
					dao.myBook(me_id);
				}else if(no==3) {//대출도서 반납
					/*
					 * 먼저 현재 대출중인 목록 보여주고
					 * ------------------------------------
					 * 번호   도서명   대출여부      등록일
					 * ------------------------------------
					 * 41     자바    대출중     2023-10-10
					 * ------------------------------------
					 *  
					 * [도서 반납하기]
					 * 대출번호:41
					 * 1건의 도서가 반납되었습니다.
					 */
					
					dao.myBook(me_id);
					
					System.out.println("[도서 반납하기]");
					System.out.print("대출번호:");
					int re_num = Integer.parseInt(br.readLine());
					int check = dao.checkRent(re_num, me_id);
					if(check == 1) {
						//여기서 반납하면됨
						dao.returnBook(re_num);
					}else {
						System.out.println("반납할 수 없는 대출 번호입니다.");
					}
				}else if(no==4) {//종료
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
		new BookUserMain();
	}
}

