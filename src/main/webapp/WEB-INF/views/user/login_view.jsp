<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
		<meta charset="UTF-8">
		<title>로그인</title>
</head>
<body>

<c:if test="${msg != null}">
	<p>${msg}</p>
</c:if>

	<form class="form-login" action = "/suribank/user/login" method = "post">
			<c:choose>
						<c:when test = "${cookie.rememberID.value == null }">
					<input type = "text" class="input" name = "id" placeholder="ID" required>
						</c:when>
					<c:otherwise>
						<input type = "text" class="input" name = "id" value =  ${cookie.rememberID.value } placeholder="ID" required>
					</c:otherwise>
			</c:choose>
					<input type = "password" class="input" name = "password" placeholder="PASSWORD" required>
					<button class="btn" type="submit">로그인</button>
		</form>
		<form class="form-login" action = "/suribank/user/signup_view" method = "post">
			<button class="btn" type="submit">회원가입</button>
		</form>
</body>
</html>