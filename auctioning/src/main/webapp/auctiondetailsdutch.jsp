<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Auction Details</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles.css' />">
</head>
<body>
    <h1>Auction Details</h1>

    <c:if test="${not empty auction}">
        <h2>${auction.itemName}</h2>
        <p><strong>Current Price:</strong> $${auction.currentPrice}</p>

        <p><strong>Item Name:</strong> ${auction.itemName}</p>
        <p><strong>Description:</strong> ${auction.description}</p>
        <p><strong>Shipping Price:</strong> $${auction.shippingprice}</p>

        <!-- Removed the highest bidder section -->
        <form action="BidServlet" method="post">
            <input type="hidden" name="auctionId" value="${auction.id}">
            <input type="hidden" id="bidAmount" name="bidAmount" value="${auction.currentPrice}">
            
            <!-- Buy Now Button -->
            <button type="submit">Buy Now</button>
        </form>
    </c:if>

    <c:if test="${empty auction}">
        <p>No auction details available.</p>
    </c:if>

</body>
</html>
