<%@page import="com.gyojincompany.dto.BoardDto"%>
<%@page import="java.util.List"%>
<%@page import="com.gyojincompany.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>   
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %> 
<%
	BoardDao bDao = new BoardDao();
	List<BoardDto> bDtos = bDao.boardList();	
	request.setAttribute("bDtos", bDtos);
%>    
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
      <a href="#" class="write-button">글쓰기</a>
    </div>
    
    <table class="board-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${bDtos}" var="bDto">
        <tr>
          <td>${bDto.bnum }</td>
          <td>
          <c:choose>
          	<c:when test="${fn:length(bDto.btitle) > 35}">
          		<a href="#">${fn:substring(bDto.btitle, 0, 35)}...</a>
          	</c:when>
          	<c:otherwise>
          		<a href="#">${bDto.btitle}</a>
          	</c:otherwise>
          </c:choose>
          </td>
          <td>${bDto.memberid }</td>
          <td>${fn:substring(bDto.bdate,0,10)}</td>
          <td>${bDto.bhit }</td>
        </tr>
        </c:forEach>
        <!-- 추가 게시글 -->
      </tbody>
    </table>
  </div>
</body>
</html>

    