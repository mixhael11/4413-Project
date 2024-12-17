<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Payment Info</title>
    <style>
        body {
            background-color: #f0f0f0;
            font-family: Arial, sans-serif;
        }

        .container {
            display: flex;
            justify-content: space-between;
            padding: 20px;
            max-width: 900px;
            margin: 0 auto;
        }

        .left-column, .right-column {
            width: 48%;
        }

        .left-column p {
            margin: 10px 0;
        }

        .right-column form {
            display: flex;
            flex-direction: column;
        }

        .right-column input[type="text"],
        .right-column input[type="number"] {
            padding: 8px;
            margin-bottom: 10px;
        }

        .right-column input[type="submit"] {
            margin-top: 10px;
        }
    </style>
</head>
<body>

<h1 align="center">Payment Info</h1>
${failed != null ? failed : ""}

<div class="container">
    <div class="left-column">
        <p><strong>First Name:</strong> ${firstName}</p>
        <p><strong>Last Name:</strong> ${lastName}</p>
        <p><strong>Street:</strong> ${street}</p>
        <p><strong>Street Number:</strong> ${streetNumber}</p>
        <p><strong>City:</strong> ${city}</p>
        <p><strong>Country:</strong> ${country}</p>
        <p><strong>Postal Code:</strong> ${postalCode}</p>
        <p><strong>Total Paid:</strong> ${totalPaid}</p>
    </div>

    <div class="right-column">
        <form action="PaymentServlet" method="post">
            <h2>Card Information</h2>
            <label for="CardNumber">Card Number:</label>
            <input type="number" name="CardNumber" required/><br/>
            
            <label for="ExpDate">Expiration Date:</label>
            <input type="number" name="ExpDate" required/><br/>
            
            <label for="SecCode">Security Code:</label>
            <input type="number" name="SecCode" required/><br/>
            
            <label for="Name">Name on Card:</label>
            <input type="text" name="Name" required/><br/>
            
            <label>Payment Method:</label><br/>
            <input type="radio" id="s1" name="checked" value="S1">
            <label for="s1"> Visa </label><br>
            
            <input type="radio" id="s2" name="checked" value="S2">
            <label for="s2"> MasterCard </label><br>  
            
            <input type="radio" id="s3" name="checked" value="S3">
            <label for="s3"> American Express </label><br>  

            <input type="submit" value="Submit"/>
        </form>
    </div>
</div>

</body>
</html>
