<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Details</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles.css' />">
</head>
<body>
    <h1>Payment Details</h1>

    <c:if test="${not empty auction}">
        <h2>${auction.itemName}</h2>
        <p><strong>Description:</strong> ${auction.description}</p>
        <p><strong>Shipping Price:</strong> $${auction.shippingprice}</p>
        <p><strong>Winning Price:</strong> $${auction.currentPrice}</p>
        
        <c:if test="${!empty auction.highestBidder}">
            <p><strong>Highest Bidder:</strong> ${auction.highestBidder}</p>
        </c:if>

        <form action="PaymentServlet" method="get">
            <input type="hidden" name="auctionId" value="${auction.id}">

            <!-- Option to add expedited shipping -->
            <label for="expeditedShipping">Add Expedited Shipping $50:</label>
            <input type="radio" name="expeditedShipping" value="yes"> Yes
            <input type="radio" name="expeditedShipping" value="no" checked> No

            <!-- Pay Now Button -->
            <button type="submit">Pay Now</button>
        </form>
    </c:if>

    <c:if test="${empty auction}">
        <p>No auction details available.</p>
    </c:if>

</body>
</html>
