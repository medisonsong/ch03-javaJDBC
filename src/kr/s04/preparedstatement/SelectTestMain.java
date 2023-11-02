package kr.s04.preparedstatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

public class SelectTestMain {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			//JDBC 수행 1,2단계 
			conn = DBUtil.getConnection();

			//SQL문 작성 (num 기준 내림차순 정렬)
			sql = "SELECT * FROM test2 ORDER BY num DESC";
			
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			System.out.println("번호\t제목\t작성자\t등록일"); // 메모, 이메일 빼고 출력
			
			while(rs.next()) {
				System.out.print(rs.getInt("num"));
				System.out.print("\t");
				System.out.print(rs.getString("title"));
				System.out.print("\t");
				System.out.print(rs.getString("name") + "\t");
				System.out.println(rs.getDate("reg_date")); //string(2023-10-06 11:15:48)으로 반환할 수 있는데 getDate라고 하면 날짜 형태(2023-10-06)로 나옴 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원 정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
}
