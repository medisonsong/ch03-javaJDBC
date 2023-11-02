package kr.s04.preparedstatement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class SelectSearchMain {
	public static void main(String[] args) {
		BufferedReader br = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			
			//검색어 입력 받기 (제목 검색)
			System.out.print("제목 검색어:");
			String keyword = br.readLine();
			
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			//SQL문 작성 (방법 2가지)
//			1) sql = "SELECT * FROM test2 WHERE title LIKE '%' || ? || '%'"; // % 가변 문자기 때문에 '%', %?% 연결해서 만들었음
			sql = "SELECT * FROM test2 WHERE title LIKE ?"; 
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
//			1) pstmt.setString(1, keyword);
			pstmt.setString(1, "%"+keyword+"%"); //이렇게 해도 됨
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			
			//검색이니 한 건이 아니라 여러 건이 될 수도 있음 --> if문이 아니라 while문으로 작성
			//없는 제목을 검색할 시에 검색어가 없다고 출력할 땐 아까랑 똑같이 if문으로 감싸면 됨
				// 근데 똑같이 while(rs.next())을 쓰면 rs.next()때문에 첫번째 행을 놓치게 됨
				// 그렇기 때문에 첫번째는 무조건 실행하는 do while문을 사용!!!
			if(rs.next()) {
				System.out.println("번호\t제목\t작성자\t날짜"); //검색 데이터가 없을 경우 출력되지 않게 하려고
				do {
					System.out.print(rs.getInt("num") + "\t");
					System.out.print(rs.getString("title") + "\t");
					System.out.print(rs.getString("name") + "\t");
					System.out.println(rs.getDate("reg_date"));
				}while(rs.next());
			}else {
				System.out.println("검색된 데이터가 없습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
			if(br!=null) try {br.close();} catch(IOException e) {}
		}
	}
}
/*
<있는 검색어 입력 시>
제목 검색어:가
번호	제목	작성자	날짜
1	가을	홍길동	2023-10-06
4	가을 여행	이순신	2023-10-06

<없는 검색어 입력 시>
제목 검색어:여름
검색된 데이터가 없습니다.

 *
 * sql문 가변문자 포함해서 검색할 때 방법이 2가지 있음
 * 
 * 1)
 * sql = "SELECT * FROM test2 WHERE title LIKE '%' || ? || '%'";
 * pstmt.setString(1, keyword);
 * 
 * 2)
 * sql = "SELECT * FROM test2 WHERE title LIKE ?"; 
 * pstmt.setString(1, "%"+keyword+"%");
 */

