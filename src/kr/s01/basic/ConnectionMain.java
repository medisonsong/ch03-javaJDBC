package kr.s01.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMain {
	public static void main(String[] args) {
		String db_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
		//jdbc-oracle주소:thin(드라이버 종류):@(ip주소):port번호:오라클식별자
		String db_id = "c##user001";
		String db_password = "1234";
		
		try {
			//JDBC 수행 1단계 : 드라이버 로드
			Class.forName(db_driver);
			//JDBC 수행 2단계 : Connection 개체 생성 (생성되면 연동됨)
			Connection conn = DriverManager.getConnection(db_url, db_id, db_password);
			System.out.println("Connection 객체가 생성되었습니다.");
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
} 
