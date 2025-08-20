package com.gyojincompany.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gyojincompany.dto.BoardDto;
import com.gyojincompany.dto.BoardMemberDto;
import com.gyojincompany.dto.MemberDto;

public class BoardDao {
	
	private String driverName = "com.mysql.jdbc.Driver"; //MySQL JDBC 드라이버 이름
	private String url = "jdbc:mysql://localhost:3306/jspdb"; //MySQL이 설치된 서버의 주소(ip)와 연결할 DB(스키마) 이름		
	private String username = "root";
	private String password = "12345";	
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public List<BoardDto> boardList() { //게시판 모든 글 리스트를 가져와서 반환하는 메서드
		//String sql = "SELECT * FROM board ORDER BY bnum DESC";
		String sql = "SELECT row_number() OVER (order by bnum ASC) AS bno,"
				+ "b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, b.bdate, b.bhit "
				+ "FROM board b "
				+ "INNER JOIN members m ON b.memberid = m.memberid"
				+ " ORDER BY bno DESC";
		//members 테이블과 board 테이블의 조인 SQL문
		//List<BoardMemberDto> bmDtos = new ArrayList<BoardMemberDto>();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)			
			
			rs = pstmt.executeQuery(); //모든 글 리스트(모든 레코드) 반환
			
			while(rs.next()) {
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");				
				String memberemail = rs.getString("memberemail");
				
				int bno = rs.getInt("bno");
				
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid); 
				memberDto.setMemberemail(memberemail); 
				
				BoardDto bDto = new BoardDto(bno, bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);
				//BoardMemberDto bmDto = new BoardMemberDto(bnum, btitle, bcontent, memberid, memberemail, bhit, bdate);
				bDtos.add(bDto);
				
			}	
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 목록 가져오기 실패!");
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
		return bDtos; //모든 글(bDto) 여러 개가 담긴 list인 bDtos를 반환
	}
	
	public void boardWrite(String btitle, String bcontent, String memberid) { //게시판에 글쓰기(글 db 입력) 메서드
		
		String sql = "INSERT INTO board(btitle, bcontent, memberid, bhit) VALUES (?,?,?,0)";
		//새글 등록이므로 조회수는 0부터 시작->bhit 초기값을 0으로 입력
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)
			pstmt.setString(1, btitle);
			pstmt.setString(2, bcontent);
			pstmt.setString(3, memberid);			
			
			pstmt.executeUpdate(); //성공하면 sqlResult 값이 1로 변환
			// SQL문을 DB에서 실행->성공하면 1이 반환, 실패면 1이 아닌 값 0이 반환
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 새글 등록 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
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
	}
	
	public BoardDto contentView(String boardnum) { //게시판 글 목록에서 유저가 클릭한 글 번호의 글 dto 반환 메서드
		String sql = "SELECT * FROM board WHERE bnum=?";
		BoardDto bDto = null;
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)			
			pstmt.setString(1, boardnum);			
			rs = pstmt.executeQuery(); //해당 번호글의 레코드 1개 또는 0개가 반환
			
			while(rs.next()) {
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				
				bDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
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
		return bDto;
	}
	
	public void boardUpdate(String bnum, String btitle, String bcontent) {
		String sql ="UPDATE board SET btitle=?, bcontent=? WHERE bnum=?";
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)
			pstmt.setString(1, btitle);
			pstmt.setString(2, bcontent);
			pstmt.setString(3, bnum);			
			
			pstmt.executeUpdate(); //성공하면 sqlResult 값이 1로 변환
			// SQL문을 DB에서 실행->성공하면 1이 반환, 실패면 1이 아닌 값 0이 반환
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 글 수정 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
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
		
	}
	
	public void boardDelete(String bnum) {
		String sql = "DELETE FROM board WHERE bnum=?";
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)
			pstmt.setString(1, bnum);				
			
			pstmt.executeUpdate(); //성공하면 sqlResult 값이 1로 변환
			// SQL문을 DB에서 실행->성공하면 1이 반환, 실패면 1이 아닌 값 0이 반환
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 글 삭제 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
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
			
		}
	
	public void updateBhit(String bnum) {
		String sql = "UPDATE board SET bhit=bhit+1 WHERE bnum=?"; //조회수가 1씩 늘어나는 sql문
		
		try {
			Class.forName(driverName); //MySQL 드라이버 클래스 불러오기			
			conn = DriverManager.getConnection(url, username, password);
			//커넥션이 메모리 생성(DB와 연결 커넥션 conn 생성)
			
			pstmt = conn.prepareStatement(sql); //pstmt 객체 생성(sql 삽입)			
			pstmt.setString(1, bnum);			
			
			pstmt.executeUpdate(); //성공하면 sqlResult 값이 1로 변환
			// SQL문을 DB에서 실행->성공하면 1이 반환, 실패면 1이 아닌 값 0이 반환
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 조회수 수정 실패!");
			e.printStackTrace(); //에러 내용 출력
		} finally { //에러의 발생여부와 상관 없이 Connection 닫기 실행 
			try {
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
	}
	
}
