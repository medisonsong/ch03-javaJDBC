package kr.util;

import java.sql.CallableStatement; //프로시저 호출할 때 사용하는 것
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement; //statement 대신 얘가 쓰임 이제 계속 평~~생 ㅎㅅㅎ

public class DBUtil { 
	// 1) 객체 생성을 따로 안해도 사용 가능하게 static하게 바꿈
	// 2) 내용을 안 바꾸니까 static한 상수로 바꿈
	// 3) 다른데서 호출 못하게 private하게 만듦 >> private static final 사용
	private static final String DB_DRIVER = "oracle.jdbc.OracleDriver";
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String DB_ID = "c##user001";
	private static final String DB_PASSWORD = "1234";
	
	//Connection 객체를 생성해서 반환
	public static Connection getConnection() throws ClassNotFoundException, SQLException { //throws 명시했음 
		//JDBC 수행 1단계 : 드라이버 로드
		Class.forName(DB_DRIVER); // class객체기 때문에 ClassNotFoundException. SQLException 예외 가능성 유 > trycatch를 하거나 throws를 명시해야함 
		
		//JDBC 수행 2단계 : Connection 객체 생성 
		return DriverManager.getConnection(DB_URL, DB_ID, DB_PASSWORD);
		// 반환타입이 Connection 인데 return 이 없으면 에러가 남! 그래서 connection 객체 생성 후 반환
	}
	
	//자원 정리
	public static void executeClose(ResultSet rs, PreparedStatement pstmt,
										Connection conn) {
		if(rs != null)try {rs.close();} catch(SQLException e) {} //select사용할 때 result set 자원정리도 자동처리 한거임 (안 쓸 경우에 null처리)
		if(pstmt != null) try {pstmt.close();} catch(SQLException e) {}
		if(conn != null) try {conn.close();} catch(SQLException e) {}
	}
	
	public static void executeClose(CallableStatement cstmt, Connection conn) { //프로시저 사용할 때 미리 만들어 놓는 것
		if(cstmt != null) try {cstmt.close();} catch(SQLException e) {}
		if(conn != null) try {conn.close();} catch(SQLException e) {}
	}
	
}
/*
 * db와 관련된 메서드를 만들고 싶다 > DBUtil 클래스를 만들었으니 여기 안에다 생성하고 사용하면 됨
 */
