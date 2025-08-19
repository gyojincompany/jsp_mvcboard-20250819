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
  <div class="board-container">
    <h2>새 글쓰기</h2>

    <form action="#" method="post" class="edit-form">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" placeholder="제목을 입력하세요" required />
      </div>

      <div class="form-group">
        <label for="author">작성자</label>
        <input type="text" id="author" name="author" placeholder="작성자명을 입력하세요" required />
      </div>

      <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" placeholder="내용을 입력하세요" required></textarea>
      </div>

      <div class="form-buttons">
        <button type="submit" class="btn btn-primary">등록</button>
        <a href="index.html" class="btn btn-secondary">취소</a>
      </div>
    </form>
  </div>
</body>
</html>
