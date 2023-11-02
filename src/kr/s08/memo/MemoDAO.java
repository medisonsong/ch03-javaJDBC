package kr.s08.memo;

/*
 * DAO: Data Access Object
 * 		데이터베이스의 데이터를 전문적으로 호출하고 제어하는 객체
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.util.DBUtil;

public class MemoDAO {
	//글쓰기
	public void insertMemo(String name, String passwd, String subject, String content, String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "INSERT INTO memo(num,name,passwd,subject,content,email,reg_date)"
					+ " VALUES(memo_seq.nextval,?,?,?,?,?,SYSDATE)";
			
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setString(1, name);
			pstmt.setString(2, passwd);
			pstmt.setString(3, subject);
			pstmt.setString(4, content);
			pstmt.setString(5, email);
			
			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행을 삽입했습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//목록 보기 (자원정리를 했기 때문에 다시 생성)
	public void selectMemo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM memo ORDER BY num DESC"; // unique한 num이나 reg_date로 정렬할 수 있음
			
			//JDBC 수행 3단계 : pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			System.out.println("----------------------------------------------------");
			
			//JDBC 수행 4단계 :sql문 실행해서 결과 행들을 resultset에 담음
			rs = pstmt.executeQuery();
			
			//테이블이 0행일 때 등록된 데이터가 없다고 출력하기 위한 조건식
			if(rs.next()) {
				System.out.println("글번호\t이름\t작성일\t제목");
				do {
					System.out.print(rs.getInt("num") + "\t");
					System.out.print(rs.getString("name") + "\t");
					System.out.print(rs.getDate("reg_date") + "\t");
					System.out.println(rs.getString("subject"));
				}while(rs.next());
			}else {
				System.out.println("등록된 데이터가 없습니다.");
			}
						
			System.out.println("----------------------------------------------------");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//상세글 보기
	public void selectDetailMemo(int num) { // num을 넣고 그걸 기준으로 한 행을 빼낸다는 것임
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//SQL문
			sql = "SELECT * FROM memo WHERE num=?";
			
			//JDBC 수행 3단계 : preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, num);
			
			//JDBC 수행 4단계 : SQL문을 실행해서 한 건의 레코드를 ResultSet에 담음
			//					행이 없으면 검색한 정보가 없다고 출력하기
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("글번호 : " + rs.getInt("num")); // getInt(num)> primary key / getInt("num")> 컬럼명 ==> 다른거라 꼭 ""넣어야함
				System.out.println("이름 : " + rs.getString("name"));
				System.out.println("비밀번호 : " + rs.getString("passwd"));
				System.out.println("제목 : " + rs.getString("subject"));
				System.out.println("내용 : " + rs.getString("content"));
				
				String email = rs.getString("email");
				if(email == null) email = "";
				System.out.println("이메일 : " + email);
				System.out.println("작성일 : " + rs.getDate("reg_date"));
			}else {
				System.out.println("검색한 정보가 없습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//글 수정
	public void updateMemo(int num, String name, String passwd, String subject, String content, String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "UPDATE memo SET name=?,passwd=?,subject=?,content=?,email=? WHERE num=?"; // 변경 가능한 것들은 다 나열하는거임
			
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setString(1, name);
			pstmt.setString(2, passwd);
			pstmt.setString(3, subject);
			pstmt.setString(4, content);
			pstmt.setString(5, email);
			pstmt.setInt(6, num);
			
			//JDBC 수행 4단계 : sql문 실행
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행을 수정했습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	//글 삭제 (num값 받고 삭제)
	public void deleteMemo(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "DELETE FROM memo WHERE num=?";
			
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?값에 데이터 바인딩
			pstmt.setInt(1, num);
			
			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			System.out.println(count + "개 행을 삭제했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
