
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>   
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %> 
  
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>약국 게시판</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <div class="board-container">  	
    <div class="board-header">
      <h2>자유게시판</h2>
      <span style="color:blue;">
      	<c:if test="${not empty sessionScope.sessionId }">
      		<b>${sessionScope.sessionId}</b>님 로그인 중
      	</c:if>
      </span>
      <a href="write.do" class="write-button">글쓰기</a>
    </div>
    
    <table class="board-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>이메일</th>
          <th>작성일</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${bDtos}" var="bDto">
        <tr>
          <td>${bDto.bnum }</td> <!-- 글 번호 -->
          <td> <!-- 글 제목 -->
          <c:choose>
          	<c:when test="${fn:length(bDto.btitle) > 35}">
          		<a href="content.do?bnum=${bDto.bnum }">${fn:substring(bDto.btitle, 0, 35)}...</a>
          	</c:when>
          	<c:otherwise>
          		<a href="content.do?bnum=${bDto.bnum }">${bDto.btitle}</a>
          	</c:otherwise>
          </c:choose>
          </td>
          <td>${bDto.memberid }</td> <!-- 글 작성자 -->
          <td>${bDto.memberDto.memberemail }</td> <!-- 작성자 이메일 -->
          <td>${fn:substring(bDto.bdate,0,10)}</td> <!-- 글 등록일 -->
          <td>${bDto.bhit }</td> <!-- 글 조회수 -->
        </tr>
        </c:forEach>
        <!-- 추가 게시글 -->
      </tbody>
    </table>
  </div>
</body>
</html>

    