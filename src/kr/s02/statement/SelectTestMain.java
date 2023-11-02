package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet; // 얘가 추가됨 결과셋 만들어서 돌려야되니까
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTestMain {
	public static void main(String[] args) {
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
		String db_id = "c##user001";
		String db_password = "1234";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			//JDBC 수행 1단계 : 드라이버 로드
			Class.forName(db_driver);
			//JDBC 수행 2단계 : Connection 객체 생성
			conn = DriverManager.getConnection(db_url, db_id, db_password);
			
			//SQL문 작성
			sql = "SELECT * FROM test1";
			
			//InsertTestMain 에서는 insert를 했으니 insert한 행의 갯수를 반환했는데
			//이제는 select기 때문에 모든 행을 읽어서 resultset에 저장.
			//JDBC 수행 3단계 : statement 객체 생성
			stmt = conn.createStatement();
			
			//JDBC 수행 4단계 : SQL문 실행해서 테이블로부터 레코드(행)을 전달받아서 결과집합을 만들고,
			//				RESULT SET객체에 담음
			rs = stmt.executeQuery(sql);
			//커서가 돌면서 loop 돌던 것처럼 자바에는 while문을 사용해서 돌림
						
			System.out.println("ID\t나이"); //항목 만든 후에 데이터 출력하기 위해서 적었음
			
			while(rs.next()) { // next가 행 바깥에 있는 커서를 다음 행으로 옮기는 역할을 함
				/*
				//컬럼명을 통해서 데이터를 반환
				System.out.print(rs.getString("id")); // id랑 매핑되어 있는 데이터를 string 타입으로 반환
				System.out.print("\t");
				System.out.println(rs.getInt("age")); // age랑 매핑되어 있는 데이터를 int 타입으로 반환
				*/
				
				//컬럼 인덱스를 통해서 데이터를 반환
				System.out.print(rs.getString(1));
				System.out.print("\t");
				System.out.println(rs.getInt(2));
				//이때 주의할 점이 자바 순서가 아니라 오라클 순으로 명시해야 함 (012~ x // 123~)
				//컬럼이 많을 경우에는 인덱스보다 컬럼명을 기재하는 게 더 좋은 방법
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리 (conn -> stmt -> rs 순으로 만들어졌기 때문에 역순으로 자원정리 함)
			if(rs != null) try {rs.close();} catch(SQLException e) {}
			if(stmt != null) try {stmt.close();} catch(SQLException e) {}
			if(conn != null) try {conn.close();} catch(SQLException e) {}
		}
		
	}
}


