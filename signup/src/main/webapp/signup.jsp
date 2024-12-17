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

<h1>Sign Up</h1>
	<form action="signup" method="post">
	
		<div class="welcome-container">

			
			<div class="left-column">
				<label for="firstName">First Name:</label> <input type="text"
					id="firstName" name="firstName" required> <label
					for="lastName">Last Name:</label> <input type="text" id="lastName"
					name="lastName" required> <label for="streetAddress">Street
					Address:</label> <input type="text" id="streetAddress" name="streetAddress"
					required> <label for="streetNumber">Street Number:</label>
				<input type="number" id="streetNumber" name="streetNumber" required>

				<label for="postalCode">Postal Code:</label> <input type="text"
					id="postalCode" name="postalCode" required> <label
					for="city">City:</label> <input type="text" id="city" name="city"
					required> <label for="country">Country:</label> <input
					type="text" id="country" name="country" required>
			</div>


			<div class="divider-inv"></div>


			<div class="right-column">
				<br /> <label for="username" class="margintop">Username:</label> <input
					type="text" id="username" name="username" required> <label
					for="password">Password:</label> <input type="password"
					id="password" name="password" required>

				<div>
					<button class="btn margintop" type="submit">Submit</button>
					<button class="btn" type="button"
						onclick="window.location.href='welcome.jsp'">Back</button>
				</div>
			</div>




		</div>
	</form>
</body>
</html>
