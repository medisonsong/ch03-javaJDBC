package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertTestMain {
	public static void main(String[] args) {
		// db_driver/ url/ id/ password를 항상 기재하기엔 무리라 추후에 메서드로 설정해서 만들거임
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
		String db_id = "c##user001";
		String db_password = "1234";
		
		//위 메서드는 변할 수 있는데 아래 구조는 항상 같음
		
		//변수선언
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1단계 : 드라이버 로드
			Class.forName(db_driver);
			//JDBC 수행 2단계 : Connection 객체 생성
			conn = DriverManager.getConnection(db_url, db_id, db_password);
			//SQL문
			sql = "INSERT INTO test1 (id, age) VALUES ('s''ing', 20)"; //얜 어디 들어가든 위치 상관없음 (try밖이어도 상관x)
			
			//JDBC 수행 3단계 : statement 객체 생성 (stmt 안에 execute가 들어감)
			stmt = conn.createStatement();
			
			//JDBC 수행 4단계 : SQL문을 실행해서 하나의 행을 삽입
			//삽입 작업 후 삽입한 행의 개수를 반환 (executeupdate) // INSERT / DELETE / UPDATE 관련 구문에서는 반영된 레코드의 건수를 반환합니다.
			
			// SQL문장이 하나 > AUTO COMMIT 사용 (기본값이 AUTO COMMIT) (ROLLBACK 명시적으로 안해도됨/ 성공하면 COMMIT, 실패하면 INSERT가 안돼서)
			// 2개 이상의 SQL문장 사용 시 수작업으로 SQL문을 실행해야함 (3개 중 1개에 예외 발생 시 (다 관련이 되어 있는 문장이라) 전체 다 무시해야함
			// 그래서 수작업으로 COMMIT ROLLBACK 해야함)
			int count = stmt.executeUpdate(sql);
			System.out.println(count + "개의 행을 추가했습니다.");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			if(stmt != null) try {stmt.close();} catch(SQLException e) {}
			if(conn != null) try {conn.close();} catch(SQLException e) {}
		}
		
	}
}

// 연속해서 2번 실행하면 계속 오라클에 행이 추가됨 (pk 설정을 안해서)
