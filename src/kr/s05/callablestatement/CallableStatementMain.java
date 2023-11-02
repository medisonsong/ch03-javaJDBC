package kr.s05.callablestatement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.CallableStatement; //프로시저는 callable사용함
import java.sql.Connection;

import kr.util.DBUtil;

public class CallableStatementMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		String sql = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("이름:");
			String name = br.readLine();
			System.out.print("급여 인상률:");
			float rate = Float.parseFloat(br.readLine()); //프로시저에서 float으로 rate 설정했기 때문에
			
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			//프로시저 호출 문장 작성
			//SQL+ SQLDeveloper에서는 exec로 실행하는데 자바에서는 그냥 call임
			sql = "{call adjust(?,?)}"; //sql = "" 안에 값을 ?? 하는건 똑같은데 {call 프로시저명(변수)} 라고 작성함
			//JDBC 수행 3단계 : CallableStatement 객체 생성
			cstmt = conn.prepareCall(sql);
			//?에 데이터 바인딩
			cstmt.setString(1, name);
			cstmt.setFloat(2, rate);
			//JDBC 수행 4단계 : 프로시저 실행
			cstmt.executeUpdate();
			System.out.println("급여 정보 수정 작업을 완료했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(cstmt, conn);
			if(br!=null) try {br.close();} catch(IOException e) {}
		}
	}
}
/*
이름:SMITH
급여 인상률:0.2
급여 정보 수정 작업을 완료했습니다.

SMITH 1000 > 변경후 > SMITH 1200
 */