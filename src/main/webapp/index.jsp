<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>우리약국에 오신 것을 환영합니다</title>
  <link rel="stylesheet" href="css/style.css" />
</head>
<body>
  <%@ include file="include/header.jsp" %>

  <section class="hero">
    <div class="container">
      <h2>건강한 삶, 우리약국과 함께하세요!</h2>
      <p>전문 약사가 친절하게 상담해 드립니다.</p>
    </div>
  </section>

  <section class="intro container">
    <h3>우리약국 소개</h3>
    <p>
      우리약국은 신뢰와 전문성을 바탕으로 고객 여러분의 건강을 책임지고 있습니다.
      다양한 건강 정보와 맞춤형 처방으로 최상의 서비스를 제공합니다.
    </p>
  </section>

  <section class="content-area container">
    <h3>컨텐츠 영역</h3>
    <p>
      이곳에 약국 관련 소식, 이벤트, 건강 정보 등 다양한 내용을 자유롭게 작성할 수 있습니다.<br />
      예를 들어, 최신 건강 팁, 신제품 안내, 고객 후기 등을 게시해 보세요.
    </p>
    <!-- 필요에 따라 컨텐츠 추가 가능 -->
  </section>

    <%@ include file="include/footer.jsp" %>
</body>
</html>

