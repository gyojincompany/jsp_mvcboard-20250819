package com.gyojincompany.command;

import com.gyojincompany.dao.BoardDao;
import com.gyojincompany.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BModifyCommand implements BCommand{
	public void excute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDao boardDao = new BoardDao();	
		
		String bnum = request.getParameter("bnum"); //유저가 수정하려고 하는 글의 번호
		String btitle = request.getParameter("title"); //유저가 수정하려고 하는 글 제목
		String memberid = request.getParameter("author"); //유저가 수정하는 글 작성자
		String bcontent = request.getParameter("content"); //유저가 수정하려고 하는 글 내용
		
		boardDao.boardUpdate(bnum, btitle, bcontent); //글 수정 메서드 호출
		
		BoardDto boardDto = boardDao.contentView(bnum); 
		//수정한 글 번호로 db에서 다시 한번 글 데이터 가져오기
		
		request.setAttribute("boardDto", boardDto);
	}
}
