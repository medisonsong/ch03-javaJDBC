package kr.s04.preparedstatement;

import java.io.BufferedReader; //번호 입력받고 출력하기 위해 bufferedreader import
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class SelectDetilMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.print("번호:");
			int num = Integer.parseInt(br.readLine()); 
			
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "SELECT * FROM test2 WHERE num = ?"; //where절을 통해 행 제한 (num값이 일치하는 1개의 행만 출력하기 위해)
			//JDBC 수행 3단계 : prepareStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, num); // 첫번째 ?에 int형 num(입력받은 번호)을 대입
			
			//JDBC 수행 4단계 : sql문장 실행 후 결과행 하나를 출력
			//while문이 아니라 if문을 써서 행이 없을 경우에는 if문으로 넣고, 있을 때에는 빠져나가게 함 (잘못된 num을 입력받았을 경우)
			rs = pstmt.executeQuery();
			if(rs.next()) { //rs가 행 안으로 진입할 수 있게 next()를 사용해줌 
				System.out.println("번호 : " + rs.getInt("num"));
				System.out.println("제목 : " + rs.getString("title"));
				System.out.println("작성자 : " + rs.getString("name"));
				System.out.println("내용 : " + rs.getString("memo"));
				
				String email = rs.getString("email"); // email이 null일 경우에 빈 문자열로 대체 (단일 if문)
				if(email==null) email = "";
				
				System.out.println("이메일 : " + email);
				System.out.println("작성일 : " + rs.getDate("reg_date"));
			}else {
				System.out.println("검색된 데이터가 없습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원 정리
			DBUtil.executeClose(rs, pstmt, conn);
			if(br!=null) try {br.close();} catch(IOException e) {}
		}
	}
}
/*
 번호:1
번호 : 1
제목 : 가을
작성자 : 홍길동
내용 : 서늘한 날씨
이메일 : test@test1.com
작성일 : 2023-10-06
 
 번호:4
검색된 데이터가 없습니다.

 번호:3
번호 : 3
제목 : 겨울
작성자 : 장영실
내용 : 여행
이메일 : 
작성일 : 2023-10-06
 */