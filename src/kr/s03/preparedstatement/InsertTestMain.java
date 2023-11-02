package kr.s03.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil; // 생성한 자동 메서드 사용할거라서 import 

public class InsertTestMain {
	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "INSERT INTO test1 (id,age) VALUES (?,?)"; //preparedstatement -> 데이터를 직접 명시하지 않고 자리만 명시함
													// ?,? : 순서값이 있는걸로 인식함 1번 물음표, 2번 물음표 이렇게
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// connection 객체가 가지고 있는 preparestatement에다가 sql을 넣고 생성하는거임 (메서드 명에 d가 없는 이유는 명명규칙때문이라 걍 외우는게 나음)
			//클래스/ 인터페이스명 -> 명사 // 메서드명 -> 동사 형태임
			
			//?에 데이터를 바인딩(묶어준다는 뜻/ 물음표 대신 값 교체)
			pstmt.setString(1, "h'ing"); //1번 ?에 데이터 전달
			pstmt.setInt(2, 90); //2번 ?에 데이터 전달
			
			//JDBC 수행 4단계 : SQL문을 실행해서 테이블에 행 추가
			int count = pstmt.executeUpdate(); //이미 pstmt에 전달됐기 때문에 또 전달하면 에러남 ()로 처리
			System.out.println(count + "개 행을 추가했습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn); // null: resertset이 없기 때문에 null 그외 pstmt, conn 처리
		}
	}
}
/*
 * star	50
 * blue	20
 * star	60
 * sky	10
 * dragon	90
 * 생성하기 (테이블 채우기)
 * 
 * sql문장 안에 직접 데이터를 안 넣는 이유 > 데이터 보안성이 취약해서
 */