<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>Welcome to Payment</title>
</head>
<body>

    <div class="welcome-message">
        <h1>Welcome to Payment</h1>
        <p>Click the button below to proceed to payment.</p>
    </div>

    <form action="OrderServlet" method="get">
        <button type="submit" class="pay-button">Go to Payment</button>
    </form>

</body>
</html>
