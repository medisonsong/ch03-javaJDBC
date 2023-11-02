package kr.s09.car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class CarDAO {
	//자동차 정보 등록
	public void insertCar(String name, String cnumber, String color, String maker, int price) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1~2단계
			conn = DBUtil.getConnection();
			
			//sql문 (name, cnumber, color, maker, price)
			sql = "INSERT INTO car(num,name,cnumber,color,maker,price,reg_date) "
					+ "VALUES (car_seq.nextval,?,?,?,?,?,SYSDATE)";
			
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, name);
			pstmt.setString(2, cnumber);
			pstmt.setString(3, color);
			pstmt.setString(4, maker);
			pstmt.setInt(5, price);
			
			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행을 삽입했습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//목록 보기
	public void selectCar() {//번호, 이름, 제조사, 등록일 표시
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			//JDBC 수행 1단계
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM car ORDER BY num DESC";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			System.out.println("---------------------------------");
			
			if(rs.next()) {
					System.out.println("번호\t이름\t제조사\t등록일");
					do {
						System.out.print(rs.getInt("num") + "\t");
						System.out.print(rs.getString("name") + "\t");
						System.out.print(rs.getString("maker") + "\t");
						System.out.println(rs.getDate("reg_date"));
						
					}while(rs.next());
				}else {
					System.out.println("등록된 데이터가 없습니다.");
				}
			System.out.println("---------------------------------");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
	}
	
	//자동차 상세 정보
	public void selectDetailCar(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			//JDBC 수행 1단계
			conn = DBUtil.getConnection();
			
			//sql문
			sql = "SELECT * FROM car WHERE num=?";
			
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, num);
			
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("번호: " + rs.getInt("num"));
				System.out.println("이름: " + rs.getString("name"));
				System.out.println("자동차 번호: " + rs.getString("cnumber"));
				System.out.println("색상: " + rs.getString("color"));
				System.out.println("제조사: " + rs.getString("maker"));
				System.out.println("가격: " + rs.getInt("price"));
				System.out.println("등록일: " + rs.getDate("reg_date"));
			}else {
				System.out.println("검색한 정보가 없습니다.");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
	}
	
	//자동차 상세 정보 수정
	public void updateCar(int num, String name, String cnumber, String color, String maker, int price) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE car SET name=?, cnumber=?, color=?, maker=?, price=? WHERE num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, cnumber);
			pstmt.setString(3, color);
			pstmt.setString(4, maker);
			pstmt.setInt(5, price);
			pstmt.setInt(6, num);
			
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행을 수정했습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//자동차 상세 정보 삭제
	public void deleteCar(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "DELETE FROM car WHERE num=?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 행을 삭제했습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
