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

	<form action="signin" method="post" class="form-container">

		<div class="siginform-content">
			<h1>Sign In</h1>
			<label for="username" class="margintop">Username:</label> <input
				type="text" id="username" name="username" required> <br /> <label
				for="password">Password:</label> <input type="password"
				id="password" name="password" required> <br />

			<div>
				<button class="btn" type="submit">Submit</button>
				<button class="btn" type="button" onclick="window.location.href='welcome.jsp'">Back</button>
			</div>

		</div>
	</form>
</body>
</html>
