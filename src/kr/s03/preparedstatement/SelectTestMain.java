package kr.s03.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class SelectTestMain {
	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM test1 ORDER BY id ASC";
			// 시퀀스로 pk 지정 후 시퀀스 정렬을 한다거나 컬럼이 많다거나 하지 않아서 걍 아이디 기준으로 오름차순
			
			//JDBC 수행 3단계 : prepareStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//위 sql문은 ?가 없기 때문에 바인딩할 필요가 없음. 그냥 sql넣으면 됨
			
			//JDBC 수행 4단계 : SQL문을 수행해서 결과 행들을 ResultSet에 담음
			rs = pstmt.executeQuery();
			System.out.println("ID\t나이");
			
			//result set 보기
			while(rs.next()) {
				System.out.print(rs.getString("id"));
				System.out.print("\t");
				System.out.println(rs.getInt("age"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

	}
}
