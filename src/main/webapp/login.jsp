<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>약국 로그인</title>
  <link rel="stylesheet" href="css/style2.css" />
</head>
<body>
  <div class="login-container">
    <div class="login-box">
      <h2>💊 약국 회원 로그인</h2>
      <form action="loginOk.do" method="POST">
        <label for="username">아이디</label>
        <input type="text" id="username" name="username" required />

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" required />

        <button type="submit">로그인</button>

        <div class="extra-options">
          <a href="#">회원가입</a> | <a href="#">비밀번호 찾기</a>
        </div>
        
        <div class="extra-options">
         	<c:if test="${param.msg == 1}">
         		<p style="color:red;">
         			아이디 또는 비밀번호가 잘못 되었습니다.
         		</p>
         	</c:if>
         	<c:if test="${param.msg == 2}">
         		<p style="color:red;">
         			로그인 한 유저만 글을 쓸 수 있습니다.
         		</p>
         	</c:if>
        </div>
        
      </form>
    </div>
  </div>
</body>
</html>
