<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String rdate = request.getParameter("rdate");//예약일
		String rtime = request.getParameter("rtime");//예약시간
		
		System.out.println("예약일:"+rdate);
		System.out.println("예약시간:"+rtime);
	
	%>
	예약일 : <%= rdate %><br>
	예약시간 : <%= rtime %><br>
	
	
</body>
</html>