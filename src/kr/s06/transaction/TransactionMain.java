package kr.s06.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.util.DBUtil;

public class TransactionMain {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		// PreparedStatement 역할이 sql문을 실어나르는 앤데 문장을 여러 개 실어나르지 못하고 마지막 하나만 실어나름 (자원정리도 못함)
		// 그래서 3개를 생성한다면 pstmt 객체도 3개 만들어야함 +수작업으로 트랜잭션 처리도 해야함 (1,2,3 명시한게 순서가 아님/ 같은 레벨이라 순서가 따로 없음) 
		String sql = null;
		
		try {
			//JDBC 수행 1~단계
			conn = DBUtil.getConnection();
			
			//트랜잭션을 수동으로 처리하기 위해서 conn의 auto commit 해제
			conn.setAutoCommit(false);
			
			//SQL문 작성
			sql = "INSERT INTO test1 VALUES('Seoul',10)"; //물음표로 명시하면 길어지니까 걍 명시 (test하는거라)
			pstmt1 = conn.prepareStatement(sql);
			pstmt1.executeUpdate();
			
			sql = "INSERT INTO test1 VALUES('Busan',20)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO test1 VALUES('Jeju',30"; //일부러 예외 발생시킴
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.executeUpdate();
			
			//정상적으로 작업이 완료되면 commit (auto commit을 해제했기 때문에 따로 넣어줘야함)
			conn.commit();
			
			System.out.println("작업 완료!!");
			
		}catch(Exception e) {
			e.printStackTrace();
			//예외가 발생했을 경우 rollback (근데 rollback했을 때도 예외 발생 가능성이 있기 때문에 try~catch문에 넣어야함)
			try {
				conn.rollback();
			}catch(SQLException se) { //그냥 e라고 넣으면 catch 안 try~catch문이라 지역변수이기 때문에 중복돼서 충돌남
				se.printStackTrace();
			}
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null); // pstmt 순서가 없기 때문에 그냥 역순으로 기재한거
			DBUtil.executeClose(null, pstmt1, conn); // conn은 마지막에만 정리하면 됨
		}
		
	}
}
/*
 * auto commit을 해제하지 않고 sql문 중 마지막 문장에 일부러 예외를 발생시키면
 * sql = "INSERT INTO test1 VALUES('China',300"; > 실행 시 누락된 콤마라고 에러가 떴는데 (korea 500 england 400 china 300(에러구문))
 * test1 테이블에 가보면 위에 2개 데이터가 들어가있음
 * 원래는 sql문 중 하나라도 에러가 난다면 작업단위를 전체 sql을 하나로 보기 때문에 셋 다 catch로 넣어야되는데 auto commit을 쓰기 때문에 sql문 하나하나 따로 하나의 작업단위로 봐서
 * 위에 2개 문장은 commit되어버리고 마지막만 에러가 나버리는거..!
 * 
 * >> 그렇기 때문에 sql문장이 여러 개가 있을 경우 하나라도 실패하면 rollback시키기 위해서
 * 전체 묶어버리는 트랜잭션을 수작업으로 작성해야함
 * 
 * auto commit 해제 후 다시 일부러 에러를 낸다면 (정상구문) 전체 다 rollback이됨
 * 
 * sql문 작성할 때 주의할 점>
 * DML/ DDL 작성 시 WHERE 절을 안적어도 정상구문이기 때문에 다 COMMIT되어버림
 * 그렇기 때문에 원 데이터를 잃지 않으려면 조심해야함
 */
