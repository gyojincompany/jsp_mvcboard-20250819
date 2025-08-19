<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>게시글 수정 - 추석 연휴 진료 시간 안내</title>
  <link rel="stylesheet" href="css/style.css" />
</head>
<body>
  <div class="board-container">
    <h2>게시글 수정</h2>

    <form action="#" method="post" class="edit-form">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="추석 연휴 진료 시간 안내" required />
      </div>

      <div class="form-group">
        <label for="author">작성자</label>
        <input type="text" id="author" name="author" value="관리자" readonly />
      </div>

      <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" name="content" rows="10" required>안녕하세요, 우리약국입니다.
		다가오는 추석 연휴 기간 동안 진료 시간은 아래와 같습니다.
		
		- 9월 28일 (월): 정상 진료
		- 9월 29일 (화): 휴진
		- 9월 30일 (수): 휴진
		- 10월 1일 (목): 정상 진료
		
		불편을 드려 죄송하며, 건강한 명절 보내시기 바랍니다.
		감사합니다.
		</textarea>
      </div>

      <div class="form-buttons">
        <button type="submit" class="btn btn-primary">저장</button>
        <a href="post.html" class="btn btn-secondary">취소</a>
      </div>
    </form>
  </div>
</body>
</html>
