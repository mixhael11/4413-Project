<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>

<link rel="stylesheet" href="styles.css">
</head>
<body>

	<div class="header">
		<img src="images/logo.png" alt="Company Logo" class="logo">
	</div>

	<c:if test="${not empty statusMessage}">
		<div
			class="status-message ${statusType == 'error' ? 'status-error' : 'status-success'}">
			${statusMessage}</div>
	</c:if>

	<div class="welcome-container">




		<div class="left-column">
			<h1>Welcome to Our Website</h1>
			<p>Start your journey with us!</p>
		</div>


		<div class="divider"></div>


		<div class="right-column">
			<button class="btn" onclick="location.href='signin.jsp'">Sign
				In</button>
			<button class="btn" onclick="location.href='signup.jsp'">Sign
				Up</button>
		</div>
	</div>
</body>
</html>
