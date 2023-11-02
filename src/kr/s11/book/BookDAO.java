/*
관리자- 도서정보 등록
사용자- 로그인/ 로그인한 상태에서 대출 가능한 도서 검색, 원하는 서적 대출, 대출도서 반납

순서대로 작업하는게 편하다함
1. 관리자 도서 등록
2. 관리자 도서 목록 보기
3. 사용자 회원가입 (로그인은 쌤이랑 같이 했음)
4. 관리자 회원 목록
5. 사용자 도서 대출
6. 관리자 대출 목록 보기
7. 사용자 my대출 목록 
8. 사용자 대출 도서 반납

순으로 진행하면 됨
*/

package kr.s11.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class BookDAO { //관리자, 사용자 연동 모두 여기서 함
	//1. 관리자-도서 등록
	public void insertBook(String bk_name, String bk_category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO book(bk_num, bk_name, bk_category) VALUES (book_seq.nextval,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bk_name);
			pstmt.setString(2, bk_category);
			
			int count = pstmt.executeUpdate();
			System.out.println(count + "건의 도서 정보를 등록했습니다.");	
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//2. 관리자-도서 목록 보기 (+reservation에 있는 대출여부도 표시)
	public void selectBook() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT b.bk_num, b.bk_category, b.bk_name, r.re_status, b.bk_regdate FROM book b LEFT OUTER JOIN reservation r ON b.bk_num = r.bk_num ORDER BY b.bk_num DESC";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println("----------------------------------------");
			if(rs.next()) {
				System.out.println("번호\t카테고리\t도서명\t대출여부\t등록일");
				System.out.println("----------------------------------------");
				do {
					System.out.print(rs.getInt("bk_num") + "\t");
					System.out.print(rs.getString("bk_category") + "\t");
					System.out.print(rs.getString("bk_name") + "\t");
					
					//처음부터 0을 넣어서 대출가능이라고 하고싶은데 못하나
					if(rs.getInt("re_status") == 1) {
						System.out.print("대출중\t");
					}else{
						System.out.print("대출가능\t");
					}
					
					System.out.println(rs.getDate("bk_regdate"));
						
				}while(rs.next());
			}else {
				System.out.println("도서 목록이 없습니다.");
			}
			System.out.println("----------------------------------------");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//4. 관리자-회원 목록 보기
	public void selectMember() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT me_id, me_name, me_phone, me_regdate FROM member";
			// id     이름      전화번호           가입일 
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("----------------------------------------");
			if(rs.next()) {
				System.out.println("ID\t이름\t전화번호\t가입일");
				System.out.println("----------------------------------------");
				do {
					System.out.print(rs.getString("me_id") + "\t");
					System.out.print(rs.getString("me_name") + "\t");
					System.out.print(rs.getString("me_phone") + "\t");
					System.out.println(rs.getDate("me_regdate"));
				}while(rs.next());
			}else {
				System.out.println("회원 정보가 없습니다.");
			}
			System.out.println("----------------------------------------");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//5. 관리자-대출 목록 보기(대출 및 반납 - 모든 데이터 표시)/ 구매내역 확인한거랑 똑같음
	public void rentBookList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT re_num, re_status, me_id, bk_name, re_regdate, "
					+ "re_modifydate from reservation r, book b where r.bk_num = b.bk_num ORDER BY re_num DESC";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("----------------------------------------");
			if(rs.next()) {
				System.out.println("번호\t대출여부\t대출자ID\t대출도서명\t대출일\t반납일");
				System.out.println("----------------------------------------");
				do {
					System.out.print(rs.getInt("re_num") + "\t");
					
					if(rs.getInt("re_status") == 1) {
						System.out.print("대출\t");
					}else {
						System.out.print("반납\t");
					}
					
					System.out.print(rs.getString("me_id") + "\t");
					System.out.print(rs.getString("bk_name") + "\t");
					System.out.print(rs.getDate("re_regdate") + "\t");
					if(rs.getDate("re_modifydate") == null) {
						System.out.println("");
					}else {
						System.out.println(rs.getDate("re_modifydate"));
					}
				}while(rs.next());
			}else {
				System.out.println("현재 대출 중인 도서가 없습니다.");
			}
			System.out.println("----------------------------------------");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//사용자-아이디 중복 체크 (중복이면 1, 미 중복시 0반환)
	public int checkId(String me_id) { //id는 문자열이라 pk로 쓰려면 중복인지 아닌지 지정해야함 (확인 안했을 시 무결성 어쩌구로 그냥 오류남 ㅠㅠ)
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0; // 중복이면 1 미중복시 0 반환해야 되기 때문에 변수 만든거임
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "SELECT me_id FROM member WHERE me_id=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, me_id);
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = 1;
			} // 이미 count를 0으로 초기화 시켰기 때문에 else 필요 없음 
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count; //값 반환
	}
	
	
	//3. 사용자-회원가입
	public void insertMember(String me_id, String me_passwd, String me_name, String me_phone) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO member(me_id, me_passwd, me_name, me_phone) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, me_id);
			pstmt.setString(2, me_passwd);
			pstmt.setString(3, me_name);
			pstmt.setString(4, me_phone);
			
			pstmt.executeUpdate();
			System.out.println("회원가입이 완료되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	//사용자-로그인 체크(로그인 성공:true/ 실패:false 반환)
	public boolean loginCheck(String me_id, String me_passwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false; //기본값 false, 실패 시 유지 / 성공하면 true
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "SELECT me_id FROM member WHERE me_id=? AND me_passwd=?"; //둘다 일치하면 하나의 행(me_id) 반환
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, me_id);
			pstmt.setString(2, me_passwd);
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				flag = true; //행이 있으면 로그인이 된거니까 flag를 true로 바꿔줌 (로그인 성공)
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return flag; //반환할거라서
	}
	
	
	//5. 사용자-도서 대출 여부 확인 (이미 대출되어 있는 도서는 또 대출하지 못하도록 하기 위해서(pk사용)
	//도서번호(bk_num)로 검색 -> re_status의 값이 0이면 대출가능 / 1이면 대출 불가능 // id중복체크랑 똑같음!
	public int checkBook(int bk_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT bk_num FROM reservation WHERE bk_num=?";
			pstmt = conn.prepareCall(sql);
			pstmt.setInt(1, bk_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = 1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	
	//5. 사용자-도서 대출 등록 (도서 대출 여부 확인 됐으면 대출가능하게 만들기)
	public void rentBook(int re_status, int bk_num, String me_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO reservation(re_num, re_status, bk_num, me_id) "
					+ "VALUES (reservation_seq.nextval,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_status);
			pstmt.setInt(2, bk_num);
			pstmt.setString(3, me_id);
			
			int count = pstmt.executeUpdate();
			System.out.println("도서 " + count + "건이 대출되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//사용자-MY 대출 목록 보기 (현재 대출한 목록만 표시)
	public void myBook(String me_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT r.re_num, bk_name, re_status, re_regdate FROM book b JOIN reservation r ON b.bk_num = r.bk_num WHERE me_id=? AND re_status=1 ORDER BY b.bk_num DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, me_id);
			
			rs = pstmt.executeQuery();
			System.out.println("----------------------------------------");
			
			if(rs.next()) {
				System.out.println("번호\t도서명\t대출여부\t등록일");
				System.out.println("----------------------------------------");
				do {
					System.out.print(rs.getInt("re_num") + "\t");
					System.out.print(rs.getString("bk_name") + "\t");
					
					//if 넣어서 대출중~ 할거임
					if(rs.getInt("re_status") == 1) {
						System.out.print("대출중\t");
					}
					
					System.out.println(rs.getDate("re_regdate"));
				}while(rs.next());
			}else {
				System.out.println("현재 대출 중인 도서가 없습니다.");
			}
			System.out.println("----------------------------------------");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	
	//사용자-도서 반납 가능 여부 체크
	//대출번호(re_num)과 회원id(me_id)를 함께 조회해서 re_status가 1= 반납 가능, re_status가 0= 반납불가능 // id중복체크/ 도서대출여부확인이랑 똑같음!
	public int checkRent(int re_num, String me_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT re_status FROM reservation WHERE re_num=? AND me_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_num);
			pstmt.setString(2, me_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = 1; //status가 1이니 반납 가능함 (대출중인 상태라)
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//사용자-도서 반납 처리 (re_num 넣어서 반납)
	public void returnBook(int re_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE reservation SET re_status=0,re_modifydate=sysdate WHERE re_num=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, re_num);

			int count = pstmt.executeUpdate();
			
			System.out.println(count + "건의 도서가 반납되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}


