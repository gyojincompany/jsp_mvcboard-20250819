package com.gyojincompany.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.gyojincompany.command.BCommand;
import com.gyojincompany.command.BModifyCommand;
import com.gyojincompany.command.BWriteCommand;
import com.gyojincompany.dao.BoardDao;
import com.gyojincompany.dao.MemberDao;
import com.gyojincompany.dao.ReservationDao;
import com.gyojincompany.dto.BoardDto;
import com.gyojincompany.dto.BoardMemberDto;
import com.gyojincompany.dto.CommentDto;
import com.gyojincompany.dto.ReservationDto;

@WebServlet("*.do")
public class BoardController extends HttpServlet {
	
	public static final int PAGE_GROUP_SIZE = 10;
	
	
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
		ReservationDao reservationDao = new ReservationDao();
		List<BoardDto> bDtos = new ArrayList<BoardDto>();
		List<BoardMemberDto> bmDtos = new ArrayList<BoardMemberDto>();
		List<ReservationDto> rDto = new ArrayList<ReservationDto>();
		List<Date> rDates = new ArrayList<Date>();
		HttpSession session = null;
		BCommand bCommand = null;
		
		
		//List<BoardDto> countDtos = new ArrayList<BoardDto>();
		
		if(comm.equals("/list.do")) { //게시판 모든 글 목록 보기 요청
			String searchType = request.getParameter("searchType");
			String searchKeyword = request.getParameter("searchKeyword");
			int page = 1;
			int totalBoardCount = 0; //모든 글의 갯수가 저장될 변수 
			
			if(request.getParameter("page") == null) { //참이면 링크타고 게시판으로 들어온 경우
				page = 1; //링크(메뉴)타고 게시판으로 들어온 경우 무조건 첫 페이지를 보여주게됨
			} else { //유저가 보고 싶은 페이지 번호를 클릭한 경우
				page = Integer.parseInt(request.getParameter("page"));
				//유저가 클릭한 유저가 보고 싶어하는 페이지의 번호
			}
			
			if(searchType != null && searchKeyword != null && !searchKeyword.strip().isEmpty()) { //유저가 검색 결과 리스트를 원하는 경우
				bDtos = boardDao.searchBoardList(searchKeyword, searchType, 1);
				if(!bDtos.isEmpty()) {
					totalBoardCount = bDtos.get(0).getBno();
				}
				bDtos = boardDao.searchBoardList(searchKeyword, searchType, page);				
				
				//1페이지 해당하는 글 목록 가져오기
				
				request.setAttribute("searchType", searchType);
				request.setAttribute("searchKeyword", searchKeyword);
			} else { //list.do->모든 게시판 글 리스트를 원하는 경우
				bDtos = boardDao.boardList(1);
				if(!bDtos.isEmpty()) {
					totalBoardCount = bDtos.get(0).getBno();
				}	
				bDtos = boardDao.boardList(page); //게시판 모든 글이 포함된 ArrayList 반환				
				
				
			}
			
			int totalPage = (int) Math.ceil((double) totalBoardCount / 10);
            //모든 글의 갯수 137->14, 437->44	
			int startPage = (((page -1) / PAGE_GROUP_SIZE) * PAGE_GROUP_SIZE) + 1;
			int endPage = startPage + (PAGE_GROUP_SIZE - 1);
			
			//마지막 페이지 그룹의 경우에는 실제 마지막 페이지로 표시
			//글의 갯수 437->44페이지, 마지막 페이지 그룹의 실제 endPage->44 변경
			if(endPage > totalPage) {
				endPage = totalPage;
				//totalPage->실제 마지막 페이지값(437->44)
			}
			
			
			System.out.println("searchType : " + searchType);
			System.out.println("searchkeyword : " + searchKeyword);
			
			System.out.println("모든 글의 수 : " + totalBoardCount);
			//1페이지의 첫번째 글의 bno값 가져오기->bno = 모든 글의 수와 동일			
			
			request.setAttribute("bDtos", bDtos);
			request.setAttribute("currentPage", page); //유저가 현재 선택한 페이지 번호
			request.setAttribute("totalPage", totalPage); //전체 글 갯수로 계산한 전체 페이지 수
			request.setAttribute("startPage", startPage); //페이지 그룹 출력시 첫번째 페이지 번호
			request.setAttribute("endPage", endPage); //페이지 그룹 출력시 마지막 페이지 번호
			
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
			bCommand = new BModifyCommand();
			bCommand.excute(request, response);
			
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
			List<CommentDto> commentDtos = boardDao.commentList(bnum);
			
			request.setAttribute("commentDtos", commentDtos);
			
			request.setAttribute("boardDto", boardDto);
						
			viewPage = "contentView.jsp";			
		} else if(comm.equals("/writeOk.do")) {
			
			bCommand = new BWriteCommand();
			bCommand.excute(request, response);
			
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
			
		} else if(comm.equals("/index.do")) { //대문(웹의 첫 페이지)
			viewPage = "index.jsp";
		} else if(comm.equals("/commentOk.do")) { //댓글 등록요청
			request.setCharacterEncoding("utf-8");
			
			String bnum = request.getParameter("bnum"); //원글
			String comment = request.getParameter("comment"); //댓글
			
			session = request.getSession();
			String commentId = (String) session.getAttribute("sessionId"); //현재 로그인되어 있는 아이디(댓글 쓴 아이디)
			System.out.println(commentId);
			boardDao.commentWrite(bnum, commentId, comment); //댓글 쓰기 실행
			
			response.sendRedirect("content.do?bnum="+bnum);
			return;
			//viewPage = "content.do";			
		} else if(comm.equals("/reservationList.do")) { //예약 리스트 보기
			LocalDate today = LocalDate.now(); //오늘 날짜
			
			rDates = reservationDao.dayCheck("tiger");
			
			System.out.println("오늘날짜 : " + today);
			for(Date rDate : rDates) {
				// Date 형식을 LocalDate 형식으로 변환하는 방법
				Calendar cal = Calendar.getInstance();
		        cal.setTime(rDate);

		        int year = cal.get(Calendar.YEAR);
		        int month = cal.get(Calendar.MONTH) + 1; // 0 ~ 11 → +1
		        int day = cal.get(Calendar.DAY_OF_MONTH);
		        
		        LocalDate reservationDate = LocalDate.of(year, month, day);				
		        System.out.println("예약 날짜 : "+ reservationDate);
				if(reservationDate.isBefore(today)) {
					System.out.println("지난 예약 날짜 : "+ rDate);					
				} else {
					System.out.println("남은 예약 날짜 : "+ rDate);
				}
								
			}
			
			
			viewPage = "reservationList.jsp";			
		} else {
			viewPage = "index.jsp";
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);		
		dispatcher.forward(request, response);
		//boardList.jsp에게 웹 서블릿 내에서 제작한 request객체를 전달한 후 viewPage(boardList.jsp)로 이동해라
	}

}
