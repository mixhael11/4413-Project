<!DOCTYPE html>
<html>
<head>
    <title>Payment Details</title>
    <style>
        body {
            display: flex;
            justify-content: space-between;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .content {
            width: 70%;
            padding: 20px;
        }
        .sidebar {
            width: 25%;
            background-color: #f7f7f7;
            padding: 20px;
            border-left: 2px solid #ccc;
        }
    </style>
</head>
<body>
    <div class="content">
        <h1>Receipt</h1>
        <p><strong>First Name:</strong> ${firstName}</p>
        <p><strong>Last Name:</strong> ${lastName}</p>
        <p><strong>Street:</strong> ${street}</p>
        <p><strong>Street Number:</strong> ${streetNumber}</p>
        <p><strong>City:</strong> ${city}</p>
        <p><strong>Country:</strong> ${country}</p>
        <p><strong>Postal Code:</strong> ${postalCode}</p>
        <p><strong>Total Paid:</strong> ${totalPaid}</p>
        <p><strong>ItemId:</strong> ${ItemID}</p>
    </div>

    <div class="sidebar">
        <p><strong>Shipping Information:</strong></p>
        <p>The item will be shipped in xxx days.</p>
    </div>
</body>
</html>
