package com.gyojincompany.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gyojincompany.dao.BoardDao;
import com.gyojincompany.dto.BoardDto;

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
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		
		if(comm.equals("/list.do")) { //게시판 모든 글 목록 보기 요청
			bDtos = boardDao.boardList(); //게시판 모든 글이 포함된 ArrayList 반환
			request.setAttribute("bDtos", bDtos);
			viewPage = "boardList.jsp";
		} else if(comm.equals("/write.do")) { //글 쓰기 폼으로 이동
			viewPage = "writeForm.jsp";
		} else if(comm.equals("/modify.do")) { //글 수정 폼으로 이동
			viewPage = "modifyForm.jsp";
		} else if(comm.equals("/delete.do")) { //글 삭제 후 글 목록으로 이동
			viewPage = "list.do";
		} else if(comm.equals("/content.do")) { //글 목록에서 선택된 글 내용이 보여지는 페이지로 이동
			viewPage = "contentView.jsp";
		} else if(comm.equals("/writeOk.do")) {
			request.setCharacterEncoding("utf-8");
			
			String btitle = request.getParameter("title"); //유저가 입력한 글 제목
			String memberid = request.getParameter("author"); //유저가 입력한 글 작성자
			String bcontent = request.getParameter("content"); //유저가 입력한 글 내용
			
			boardDao.boardWrite(btitle, bcontent, memberid); //새 글이 DB 입력			
			viewPage = "list.do";
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);		
		dispatcher.forward(request, response);
		//boardList.jsp에게 웹 서블릿 내에서 제작한 request객체를 전달한 후 viewPage(boardList.jsp)로 이동해라
	}

}
