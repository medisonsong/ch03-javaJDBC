package kr.s04.preparedstatement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil; 

public class InsertTestMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("제목:");
			String title = br.readLine();
			System.out.print("이름:");
			String name = br.readLine();
			System.out.print("메모:");
			String memo = br.readLine();
			System.out.print("이메일:");
			String email = br.readLine();
			
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성 (num <- test2_seq.nextval, reg_date <- sysdate) 얘네는 데이터가 아니라서 ? X
			sql = "INSERT INTO test2(num, title, name, memo, email, reg_date) VALUES (test2_seq.nextval, ?, ?, ?, ?, sysdate)";
			
			//JDBC 수행 3단계 : preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 바인딩 / SetString사용
			pstmt.setString(1, title);
			pstmt.setString(2, name);
			pstmt.setString(3, memo);
			pstmt.setString(4, email);
			
			//JDBC 수행 4단계 : sql문 실행 +count에 담고 ~개의 행을 추가했습니다.
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행을 추가했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원 정리
			DBUtil.executeClose(null, pstmt, conn);
			if(br!=null) try{br.close();} catch(IOException e) {} //bufferedreader 원칙은 자원정리 하는거라 추가
		}
	}
}
