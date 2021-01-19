<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
		<meta charset="UTF-8">
		<title>회원가입</title>
</head>

<html>

<body>
	<form class="form-signUp" name = "joinForm" method = "post" action = "/suribank/signup">
		<div class="id-form-con">
			<input class="input" type = "text" name ="id" placeholder="아이디를 입력하세요." required>
			<input class="btn" type = "button" value = "중복확인"">
		</div>
		<input class="input" type = "password" name = "password" placeholder="비밀번호를 입력하세요." required>
		<input class="input" type = "password" name = "repassword" placeholder="비밀번호를 다시 입력하세요." required>
		<input class="input" type = "text" name = "name" placeholder = "이름을 입력하세요." required>
		<input class="input" type = "text" name = "nickname" placeholder = "별명을 입력하세요." required>
		<input class="input" type = "text" name = "email" placeholder = "이메일 입력하세요." required>
		<button class="btn" type="submit">가입</button>
	</form>
</body>
</html>