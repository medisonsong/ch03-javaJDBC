package kr.s02.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

public class DeleteTestMain {
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
			sql = "DELETE FROM test1";
			//JDBC 수행 3단계 : Statement 객체 생성
			stmt = conn.createStatement();
			//JDBC 수행 4단계 : sql문을 실행해서 테이블의 행을 삭제
			//				행을 삭제한 후 삭제한 행의 개수 반환
			int count = stmt.executeUpdate(sql);
			System.out.println(count + "개 행을 삭제했습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(stmt != null) try {stmt.close();} catch(SQLException e) {}
			if(conn != null) try {conn.close();} catch(SQLException e) {}
		}
	}
}
//DELETE FROM test1 WHERE id = 'sky' > 2개 행을 삭제했습니다.
//DELETE FROM test1 WHERE id = 'test' > 0개 행을 삭제했습니다. (에러 x)
//DELETE FROM test1 > 3개 행을 삭제했습니다.



/* executeQuery : resultset 객체의 값 반환 + SELECT 구문을 수행할 때 사용되는 함수
   ExecuteUpdate : Int타입으로 값 반환 / SELECT 구문을 제외한 다른 구문을 수행할 때 사용되는 함수
 -> INSERT / DELETE / UPDATE 관련 구문에서는 반영된 레코드의 건수를 반환합니다.
 -> CREATE / DROP 관련 구문에서는 -1 을 반환합니다.
*/

