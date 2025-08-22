package com.gyojincompany.command;

import com.gyojincompany.dao.BoardDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BWriteCommand implements BCommand{
	
	public void excute(HttpServletRequest request, HttpServletResponse response) {
		BoardDao boardDao = new BoardDao();		
		
		String btitle = request.getParameter("title"); //유저가 입력한 글 제목
		String memberid = request.getParameter("author"); //유저가 입력한 글 작성자
		String bcontent = request.getParameter("content"); //유저가 입력한 글 내용
		
		boardDao.boardWrite(btitle, bcontent, memberid); //새 글이 DB 입력
	}
	
	

}
