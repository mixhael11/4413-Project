<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Item Search</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/styles.css' />">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="scripts/AJAX.js"></script>
    

</head>
<body>

    <div id="header">
        <img id="logo" src="images/logo.png" alt="Logo"> <!-- Change the logo path -->
    </div>
    <div id="event">
        <p>${auctionStatus}</p>
    </div>

    <div id="greeting">
        <p>Hello, ${firstName}</p>
    </div>

    <div class="search-container">
        <form action="CatalogueServlet" method="get">
            <label for="item-search">Item Search:</label>
            <input type="text" id="item-search" name="query" placeholder="Enter item name">
            <button type="submit">Search</button>
        </form>
    </div>
    

</body>
</html>
