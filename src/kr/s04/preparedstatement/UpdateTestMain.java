package kr.s04.preparedstatement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

public class UpdateTestMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("번호:");
			int num = Integer.parseInt(br.readLine());
			System.out.print("제목:");
			String title = br.readLine();
			System.out.print("이름:");
			String name = br.readLine();
			System.out.print("메모:");
			String memo = br.readLine();
			System.out.print("이메일:");
			String email = br.readLine();
			
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			System.out.println("test2 테이블의 데이터를 수정합니다.");
			
			//SQL문 작성
			//콘솔에서는 특정(ex-이메일만 변경) 항목만 update(수정)할 때에도 모든 데이터를 명시하고 변경해야함
			//그 테이블에 컬럼수가 많다면 모두 조건 체크를 해야하는데 그럼 sql문들이 너무 많아지기 때문
			//그 수를 좀 줄이기 위해서 수정 안 하는 데이터들도 수정하는 데이터들이랑 다 넘기고 업데이트를 하는 것임 // 자바에서는 한 행 전체를 명시하고 변경해야함
			sql = "UPDATE test2 SET title=?, name=?, memo=?, email=? WHERE num = ?";
			
			//JDBC 수행 3단계 : preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, title);
			pstmt.setString(2, name);
			pstmt.setString(3, memo);
			pstmt.setString(4, email);
			pstmt.setInt(5, num); // 위 sql문에 5번째가 num이어서
			
			//JDBC 수행 4단계 : sql문 실행
			int count = pstmt.executeUpdate();
			System.out.println(count + "개 행의 정보를 수정했습니다.");  // num을 기준으로 변경할건데 pk라 하나의 행밖에 변경하지 못함
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
			if(br!=null) try {br.close();} catch(IOException e) {}
		}
	}
}
