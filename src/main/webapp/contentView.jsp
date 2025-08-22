<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>    
<%
	//if(request.getAttribute("msg") != null) { //웹서블릿에서 넘겨준 값을 뺄때는 getAttribute 사용
	//	String msginfo = request.getAttribute("msg").toString();		
	//	out.println("<script>alert('" + msginfo + "');window.location.href='list.do';</script>");	
	//}
	
	if(request.getParameter("msg") != null) {
		out.println("<script>alert('해당 글은 존재하지 않는 글입니다!');window.location.href='list.do';</script>");	
	}
%>    
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>게시글 상세 - 추석 연휴 진료 시간 안내</title>
  <link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<%@ include file="include/header.jsp" %>

  <div class="board-container">
    <h2>${boardDto.btitle }</h2>
    
    <div class="post-meta">
      <span>작성자: ${boardDto.memberid }</span> |
      <span>이메일: ${boardDto.memberDto.memberemail }</span> |
      <span>작성일: ${boardDto.bdate }</span> |
      <span>조회수: ${boardDto.bhit }</span>
    </div>
    
    <div class="post-content">
      <p>
        ${boardDto.bcontent }
      </p>
    </div>
    
    <div class="post-buttons">
      <a href="list.do" class="btn btn-secondary">목록으로</a>
      <c:if test="${sessionScope.sessionId == boardDto.memberid }">
      <a href="modify.do?bnum=${boardDto.bnum }" class="btn btn-primary">수정</a>
      <a href="delete.do?bnum=${boardDto.bnum }" class="btn btn-danger">삭제</a>
      </c:if>
    </div>
  </div>
  <%@ include file="include/footer.jsp" %>
</body>
</html>
