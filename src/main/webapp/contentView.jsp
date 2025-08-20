<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	if(request.getAttribute("msg") != null) { //웹서블릿에서 넘겨준 값을 뺄때는 getAttribute 사용
		out.println("<script>alert('존재하지 않는 글입니다!');window.location.href='list.do';</script>");	
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
  <div class="board-container">
    <h2>${boardDto.btitle }</h2>
    
    <div class="post-meta">
      <span>작성자: ${boardDto.memberid }</span> |
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
      <a href="modify.do" class="btn btn-primary">수정</a>
      <a href="delete.do" class="btn btn-danger">삭제</a>
    </div>
  </div>
</body>
</html>
