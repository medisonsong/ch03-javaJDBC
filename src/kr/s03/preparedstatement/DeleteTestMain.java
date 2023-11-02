package kr.s03.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

public class DeleteTestMain {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "DELETE FROM test1 WHERE id=?";
			
			//JDBC 수행 3단계 : prepareStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, "star");
			
			//JDBC 수행 4단계 : sql문을 실행해서 테이블의 행을 삭제
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행을 삭제했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
}
//2개의 행을 삭제했습니다.
//삭제 후 한번 더 실행 시 오류는 안 나고 0개의 행을 삭제했습니다. 라고 출력됨.
