
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
    <form action="list.do" method="get">
    	<select name="searchType">
    		<option value="btitle" ${searchType == 'btitle' ? 'selected' : ''}>제목</option>
    		<option value="bcontent" ${searchType == 'bcontent' ? 'selected' : ''}>내용</option>
    		<option value="b.memberid" ${searchType == 'b.memberid' ? 'selected' : ''}>작성자</option>    		
    	</select>
    	<input type="text" name="searchKeyword" value="${searchKeyword != null ? searchKeyword : ''}" placeholder="검색어 입력">
    	<input type="submit" value="검색">
    </form>
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
          <td>${bDto.bno }</td> <!-- 글 번호 -->
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
    <br>
    <!-- 1 페이지로 이동 화살표  -->
    <c:if test="${currentPage > 1}">
    <a href="list.do?page=1&searchType=${searchType}&searchKeyword=${searchKeyword}"> ◀◀ </a>
    </c:if>
    <!-- 이전 페이지 그룹으로 이동 화살표  -->
    <c:if test="${startPage > 1 }">
    <a href="list.do?page=${startPage-1}&searchType=${searchType}&searchKeyword=${searchKeyword}"> ◀ </a>
    </c:if>
    <!-- 페이지 번호 그룹 출력 -->
    <span>
	    <c:forEach begin="${startPage }" end="${endPage }" var="i">
	    	<c:choose>
	    		<c:when test="${i == currentPage }">
	    			<span><a href="list.do?page=${i}&searchType=${searchType}&searchKeyword=${searchKeyword}"> <b style="color:red;">[${i}]</b> </a></span>
	    		</c:when>
	    		<c:otherwise>
	    			<span><a href="list.do?page=${i}&searchType=${searchType}&searchKeyword=${searchKeyword}"> [${i}] </a></span>
	    		</c:otherwise>
	    	</c:choose>
	    </c:forEach>
    </span>
    
    <!-- 다음 페이지 그룹으로 이동 화살표  -->
    <c:if test="${endPage < totalPage }">
    <a href="list.do?page=${endPage + 1 }&searchType=${searchType}&searchKeyword=${searchKeyword}"> ▶ </a>
    </c:if>
    <!-- 마지막 페이지로 이동 화살표  -->
    <c:if test="${currentPage < totalPage }">
    <a href="list.do?page=${totalPage}&searchType=${searchType}&searchKeyword=${searchKeyword}"> ▶▶ </a>
    </c:if>
  </div>
</body>
</html>

    