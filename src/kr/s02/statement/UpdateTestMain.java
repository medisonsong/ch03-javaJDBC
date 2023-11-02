package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTestMain {
	public static void main(String[] args) {
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
		String db_id = "c##user001";
		String db_password = "1234";
		
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1단계 : 드라이버 로드
			Class.forName(db_driver);
			//JDBC 수행 2단계 : Connection 객체 생성
			conn = DriverManager.getConnection(db_url, db_id, db_password);
			
			//SQL문 작성
			sql = "UPDATE test1 SET age = 100";
			
			//JDBC 수행 3단계 : statement 객체 생성
			stmt = conn.createStatement();
			
			//JDBC 수행 4단계 : SQL문을 실행해서 하나의 행의 정보를 수정함
			//				수정한 행의 개수를 반환
			int count = stmt.executeUpdate(sql);
			System.out.println(count + "개 행의 정보를 수정했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원 정리
			if(stmt != null) try {stmt.close();} catch(SQLException e) {}
			if(conn != null) try {conn.close();} catch(SQLException e) {}
		}
	}
}

//UPDATE test1 SET age = 90 WHERE id = 'sky'> 2개 행의 정보를 수정했다고 출력됨. (sky 아이디가 2갠데 2개 다 변경됨)
//UPDATE test1 SET age = 100 WHERE id = 'wave' > wave도 100살로 바꿈
//UPDATE test1 SET age = 100 WHERE id = 'test' > 없는 아이디로 입력했을 시 0개 행의 정보를 수정했습니다. 라고 뜸 (에러는 안 남)

//java내에서 sql문 실행하면 무조건 auto commit이기 때문에 조심해야함
//UPDATE test1 SET age = 100 > where절 없이 넣었을 때 -- 5개 행의 정보를 수정했습니다. // 다 100살이 되어버림 + 되돌릴 수 없음

