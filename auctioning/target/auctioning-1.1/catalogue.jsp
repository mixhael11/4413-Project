<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Auction Catalogue</title>

    <!-- Link to external CSS -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles.css' />">

    <!-- Link to external JavaScript file -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="scripts/AJAX.js"></script>
</head>
<body>
    <h1>Welcome, ${firstName}!</h1>
    <h1>Auction Catalogue</h1>
    <form action="setNameServlet" method="get">
        <input type="hidden" name="tabId" value="${tabId}" />
        <button type="submit">Back</button>
    </form>
    <c:if test="${not empty auctions}">
        <form action="InfoServlet" method="post">
            <!-- Display items matching search keyword -->
            <table id="auctionTable" border="1">
                <thead>
                    <tr>
                        <th>Item Name</th>
                        <th>Current Price</th>
                        <th>Auction Type</th>
                        <th>Remaining Time</th>
                        <th>Select Item</th>
                    </tr>
                </thead>
                <tbody id="auction-table-body">
                    <c:forEach var="auction" items="${auctions}">
                        <tr>
                            <td>${auction.itemName}</td>
                            <td>${auction.currentPrice}</td>
                            <td>${auction.auctionType}</td>
                            <td>
                                <c:if test="${auction.auctionType == 'Forward'}">
                                    <span class="remaining-time">${auction.remainingTime} mins</span>
                                </c:if>
                                <c:if test="${auction.auctionType != 'Forward'}">
                                    NOW
                                </c:if>
                            </td>
                            <td>
                                <input type="radio" name="selectedAuctionId" value="${auction.id}" required>
                            </td>
                            
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- BID button -->
            <button type="submit">BID</button>
        </form>
    </c:if>

    <c:if test="${empty auctions}">
        <p>No auctions found for the given search criteria.</p>
    </c:if>
    <c:if test="${not empty query}">
    <input type="hidden" id="searchQuery" value="${query}" />
    </c:if>

</body>
</html>
