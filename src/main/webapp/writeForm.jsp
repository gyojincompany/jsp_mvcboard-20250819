<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>새 글쓰기</title>
  <link rel="stylesheet" href="css/style.css" />
</head>
<body>
	<%@ include file="include/header.jsp" %>
  <div class="board-container">
    <h2>새 글쓰기</h2>

    <form action="writeOk.do" method="post" class="edit-form">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" placeholder="제목을 입력하세요" required />
      </div>

      <div class="form-group">
        <label for="author">작성자</label>
        <input type="text" id="author" name="author" value="${sessionScope.sessionId }" readonly />
      </div>

      <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" placeholder="내용을 입력하세요" required></textarea>
      </div>

      <div class="form-buttons">
        <button type="submit" class="btn btn-primary">등록</button>
        <a href="list.do" class="btn btn-secondary">취소</a>
      </div>
    </form>
  </div>
  <%@ include file="include/footer.jsp" %>
</body>
</html>
