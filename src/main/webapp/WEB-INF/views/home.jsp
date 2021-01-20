<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<c:if test="${empty sessionScope.user }">
<p> 아니 왜 세션이 없냐고 </p>
</c:if>

<P>  The time on the server is ${serverTime}. </P>
<P> ${msg}</P>
<P>  아이디 : ${user.id} 이름 : ${user.name} 닉네임 : ${user.nickname} </P>

 <form action = "/suribank/user/logout" method = "post">
 	<button type="submit">로그아웃</button>
 </form>

</body>
</html>
