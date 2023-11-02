package kr.s04.preparedstatement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

/*
 * DB 연동 작업
 * CRUD 작업 - DML문장
 * Create   - INSERT
 * Read     - SELECT
 * Update   - UPDATE
 * Delete   - DELETE
 */

public class DeleteTestMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("번호:");
			int num = Integer.parseInt(br.readLine());
			
			//JDBC 수행 1~2단계		
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM test2 WHERE num=?";
			//JDBC 수행 3단계 : preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, num);
			//JDBC 수행 4단계 : SQL문 실행
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행을 삭제했습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
			if(br!=null) try {br.close();} catch(IOException e) {}
		}
	}
}
// 2번 행 삭제 후 또 삭제를 하면 0개의 행을 삭제했습니다. 라고 뜸 (에러x)