package kr.s02.statement;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DropTableMain {
	public static void main(String[] args) {
		//ddl > alter create drop 
		//dml > insert update select delete  모두 가능
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
		String db_id = "c##user001";
		String db_password = "1234";
		
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1단계 : DRIVER 로드
			Class.forName(db_driver);
			//JDBC 수행 2단계 : Connection 객체 생성
			conn = DriverManager.getConnection(db_url, db_id, db_password);
			
			System.out.println("test1 테이블 제거를 시작합니다.");
			
			//sql문 작성
			sql = "DROP TABLE test1";
			
			//JDBC 수행 3단계 : Statement 객체 생성
			stmt = conn.createStatement();
			
			//JDBC 수행 4단계 : sql문을 실행해서 테이블을 삭제함
			stmt.executeUpdate(sql);
			System.out.println("테이블이 정상적으로 삭제되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			if(stmt != null) try {stmt.close();} catch(SQLException e) {}
			if(conn != null) try {conn.close();} catch(SQLException e) {}
		}
	}
}

/* [정상 수행 시]
 * test1 테이블 제거를 시작합니다.
 * 테이블이 정상적으로 삭제되었습니다. 라고 출력됨 / 오라클에도 table자체가 삭제됨
 * 한 번 더 실행하면 ORA-00942: 테이블 또는 뷰가 존재하지 않습니다 라고 에러남
 */


