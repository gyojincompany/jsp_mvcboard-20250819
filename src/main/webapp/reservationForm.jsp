<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방문 예약 양식</title>
</head>
<body>
	<!-- 로그인 성공한 상태에서만 예약 가능하게 -->
	<h2>방문 예약하기</h2>
	<hr>
	<form action="reservationOk.jsp" method="post">
		이름 : <input type="text" name="rname" required><br><br>
		전화번호 : <input type="text" name="rphone" required="required"><br><br>
		예약일 : <input type="date" name="rdate" required="required"><br><br>
		예약시간 : <input type="time" name="rtime" required="required"><br><br>
		<input type="submit" value="예약하기">
	</form>
</body>
</html>