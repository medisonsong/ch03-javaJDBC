package kr.s02.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableMain {
	public static void main(String[] args) {
		//자바에서 create, alter, drop 문장 모두 사용 가능
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
		String db_id = "c##user001";
		String db_password = "1234";
		
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		
		try {//jdbc를 연동할때는 1~4단계를 모두 순서대로 꼭 수행해야함
			//JDBC 수행 1단계 : 드라이버 로드
			Class.forName(db_driver);
			//JDBC 수행 2단계 : Connection 객체 생성
			conn = DriverManager.getConnection(db_url, db_id, db_password);
			System.out.println("test1 테이블을 생성합니다.");
			/*
			 * 테이블을 생성하는 SQL문
			 * 접속한 계정에 테이블명이 중복되면 에러가 발생하기 때문에
			 * 동일 계정에서는 한번만 수행함
			 */
			sql = "CREATE TABLE test1 (id VARCHAR2(10), age NUMBER)";
			
			//3~4단계에서 sql문을 오라클에 전달할거임
			//JDBC 수행 3단계: Statement 객체 생성
			stmt = conn.createStatement();
			
			//JDBC 수행 4단계: SQL문을 실행해서 DB에 테이블을 생성
			stmt.executeUpdate(sql);
			
			System.out.println("테이블이 정상적으로 생성되었습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {//웹사이트로 넘어가게 되면 자원정리를 안하면 속도가 점점 느려지니 db랑 연동할 땐 자원정리 (close)필수임
			//conn 생성 후 stmt를 생성했기 때문에 역순으로 정리
			if(stmt!=null) try {stmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
		}
	}
}
//실행 후 oracle로 가서 테이블 새로고침하면 test1 테이블이 생성되어있음
//한 번 더 실행하게 되면 SQLSyntaxErrorException이 일어남 / 기존 객체가 이름을 사용하고 있다고.
