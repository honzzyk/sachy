<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='styles/default.css' />" media="screen, projection" />
 
<title>Přihlášení</title>
<style type="text/css">
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

#login-box {
	width: 250px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}

table {
	width: 100%;
}

h1 {
	font-size: 150%;
	margin-bottom: 1em;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>
	<div id="login-box">

		<h1>Přihlášení</h1>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'>
			<table>
				<tr>
					<td>Jméno:</td>
					<td><input type='text' name='username' required="required"></td>
				</tr>
				<tr>
					<td>Heslo:</td>
					<td><input type='password' name='password' required="required" /></td>
				</tr>
				<tr style="text-align: center; padding-top: 1em;">
					<td colspan='2'><input name="submit" type="submit" value="Přihlásit se!" /></td>
				</tr>
			</table>
		</form>
		<p style="padding-top:1em;"> 
			<a style="color: black;" href="<c:url value='/' />">Zpět na web</a>
		</p>
	</div>

</body>
</html>
