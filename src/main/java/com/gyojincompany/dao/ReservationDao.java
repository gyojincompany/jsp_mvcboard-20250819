package com.gyojincompany.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {
	private String driverName = "com.mysql.jdbc.Driver"; //MySQL JDBC 드라이버 이름
	private String url = "jdbc:mysql://localhost:3306/jspdb"; //MySQL이 설치된 서버의 주소(ip)와 연결할 DB(스키마) 이름		
	private String username = "root";
	private String password = "12345";	
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public List<Date> dayCheck(String mid) { //로그인 성공 여부를 반환하는 메서드
		String sql = "SELECT * FROM reservation WHERE memberid=?";		
		Date rdate = null;
		List<Date> rdates = new ArrayList<Date>();
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)			
			pstmt.setString(1, mid);			
			rs = pstmt.executeQuery(); //아이디와 비번이 일치하는 레코드 1개 또는 0개가 반환
			
			
			
			while(rs.next()) { //참이면 로그인 성공				
				rdate = rs.getDate("rdate");
				
				rdates.add(rdate);
			} 
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 글 가져오기 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
				if(rs != null) { //rs가 null 이 아니면 닫기(pstmt 닫기 보다 먼저 실행)
					rs.close();
				}				
				if(pstmt != null) { //stmt가 null 이 아니면 닫기(conn 닫기 보다 먼저 실행)
					pstmt.close();
				}				
				if(conn != null) { //Connection이 null 이 아닐 때만 닫기
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return rdates; //예약날짜들이 들어있는 리스트
	}
}
