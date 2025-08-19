<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        <tr>
          <td>1</td>
          <td><a href="#">추석 연휴 진료 시간 안내</a></td>
          <td>관리자</td>
          <td>2025-08-10</td>
          <td>135</td>
        </tr>
        <tr>
          <td>2</td>
          <td><a href="#">독감 예방접종 시작 안내</a></td>
          <td>관리자</td>
          <td>2025-08-05</td>
          <td>289</td>
        </tr>
        <tr>
          <td>3</td>
          <td><a href="#">건강기능식품 할인 행사</a></td>
          <td>관리자</td>
          <td>2025-07-28</td>
          <td>97</td>
        </tr>
        <!-- 추가 게시글 -->
      </tbody>
    </table>
  </div>
</body>
</html>

    