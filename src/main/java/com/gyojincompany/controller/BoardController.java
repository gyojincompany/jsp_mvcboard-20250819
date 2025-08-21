package com.gyojincompany.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gyojincompany.dao.BoardDao;
import com.gyojincompany.dao.MemberDao;
import com.gyojincompany.dto.BoardDto;
import com.gyojincompany.dto.BoardMemberDto;

@WebServlet("*.do")
public class BoardController extends HttpServlet {
	
    public BoardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		//System.out.println("uri : " + uri);
		String conPath = request.getContextPath();
		//System.out.println("conPath : " + conPath);
		String comm = uri.substring(conPath.length()); //최종 요청 값
		System.out.println("comm : " + comm);
		
		String viewPage = null;
		BoardDao boardDao = new BoardDao();
		MemberDao memberDao = new MemberDao();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		List<BoardMemberDto> bmDtos = new ArrayList<BoardMemberDto>();
		HttpSession session = null;
		
		if(comm.equals("/list.do")) { //게시판 모든 글 목록 보기 요청
			String searchType = request.getParameter("searchType");
			String searchKeyword = request.getParameter("searchKeyword");
			
			if(searchType != null && searchKeyword != null && !searchKeyword.strip().isEmpty()) { //유저가 검색 결과 리스트를 원하는 경우
				bDtos = boardDao.searchBoardList(searchKeyword, searchType);
			} else { //list.do->모든 게시판 글 리스트를 원하는 경우
				bDtos = boardDao.boardList(); //게시판 모든 글이 포함된 ArrayList 반환
			}
			
			System.out.println("searchType : " + searchType);
			System.out.println("searchkeyword : " + searchKeyword);
			
			
			request.setAttribute("bDtos", bDtos);
			viewPage = "boardList.jsp";
		} else if(comm.equals("/write.do")) { //글 쓰기 폼으로 이동
			session = request.getSession();
			String sid = (String) session.getAttribute("sessionId");
			if(sid != null) { //로그인 한 상태
				viewPage = "writeForm.jsp";
			} else { //로그인 하지 않은 상태
				response.sendRedirect("login.do?msg=2");
				return; //멈춤
			}			
		} else if(comm.equals("/modify.do")) { //글 수정 폼으로 이동
			session = request.getSession();
			String sid = (String) session.getAttribute("sessionId");			
			
			String bnum = request.getParameter("bnum"); //수정하려고 하는 글의 글번호
			BoardDto boardDto = boardDao.contentView(bnum); //수정하려고 하는 글 가져오기
			
			if(boardDto.getMemberid().equals(sid)) { //참이면 수정, 삭제 가능
				request.setAttribute("boardDto", boardDto);			
				viewPage = "modifyForm.jsp";
			} else {
				response.sendRedirect("modifyForm.jsp?error=1");
				return;
			}
			
		} else if(comm.equals("/modifyOk.do")) { //글 수정한 후 글 내용 확인 페이지로 이동
			request.setCharacterEncoding("utf-8");
			
			String bnum = request.getParameter("bnum"); //유저가 수정하려고 하는 글의 번호
			String btitle = request.getParameter("title"); //유저가 수정하려고 하는 글 제목
			String memberid = request.getParameter("author"); //유저가 수정하는 글 작성자
			String bcontent = request.getParameter("content"); //유저가 수정하려고 하는 글 내용
			
			boardDao.boardUpdate(bnum, btitle, bcontent); //글 수정 메서드 호출
			
			BoardDto boardDto = boardDao.contentView(bnum); 
			//수정한 글 번호로 db에서 다시 한번 글 데이터 가져오기
			
			request.setAttribute("boardDto", boardDto);
			
			viewPage = "contentView.jsp";
		} else if(comm.equals("/delete.do")) { //글 삭제 후 글 목록으로 이동
			String bnum = request.getParameter("bnum"); //유저가 삭제할 글의 번호
			session = request.getSession();
			String sid = (String) session.getAttribute("sessionId");
			
			BoardDto boardDto = boardDao.contentView(bnum); //수정하려고 하는 글 가져오기
			
			if(boardDto.getMemberid().equals(sid)) { //참이면 수정, 삭제 가능
				boardDao.boardDelete(bnum); //해당 글 번호 삭제 메서드 호출				
				viewPage = "list.do";
			} else {
				response.sendRedirect("modifyForm.jsp?error=1");
				return;
			}
			
		} else if(comm.equals("/content.do")) { //글 목록에서 선택된 글 내용이 보여지는 페이지로 이동
			String bnum = request.getParameter("bnum"); //유저가 선택한 글의 번호
			
			//조회수 올려주는 메서드 호출
			boardDao.updateBhit(bnum); //조회수 증가
			
			BoardDto boardDto = boardDao.contentView(bnum); //boardDto 반환(유저가 선택한 글번호에 해당하는 dto반환)
			
			if(boardDto == null) { //해당 글이 존재하지 않음
				response.sendRedirect("contentView.jsp?msg=1");
				return;
			} 
			
			request.setAttribute("boardDto", boardDto);
						
			viewPage = "contentView.jsp";			
		} else if(comm.equals("/writeOk.do")) {
			request.setCharacterEncoding("utf-8");
			
			String btitle = request.getParameter("title"); //유저가 입력한 글 제목
			String memberid = request.getParameter("author"); //유저가 입력한 글 작성자
			String bcontent = request.getParameter("content"); //유저가 입력한 글 내용
			
			boardDao.boardWrite(btitle, bcontent, memberid); //새 글이 DB 입력
			response.sendRedirect("list.do"); //포워딩을 하지 않고 강제로 list.do로 이동
			return; //프로그램의 진행 멈춤
		} else if(comm.equals("/login.do")) {
			viewPage = "login.jsp";
		} else if(comm.equals("/loginOk.do")) {
			request.setCharacterEncoding("utf-8");
			String loginId = request.getParameter("username");
			String loginPw = request.getParameter("password");
			
			int loginFlag = memberDao.loginCheck(loginId, loginPw); 
			//로그인 성공이면 1, 실패면 0이 반환
			if(loginFlag == 1) {
				session = request.getSession();
				session.setAttribute("sessionId", loginId);
			} else {
				response.sendRedirect("login.do?msg=1");
				return;
			}
			
			viewPage = "list.do";
		} else {
			viewPage = "index.jsp";
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);		
		dispatcher.forward(request, response);
		//boardList.jsp에게 웹 서블릿 내에서 제작한 request객체를 전달한 후 viewPage(boardList.jsp)로 이동해라
	}

}
