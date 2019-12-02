<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <link href="css/Bootstrap.css" rel="stylesheet" />
    <link href="css/MainPage.css" rel="stylesheet" />
    <title>Your Items</title>
</head>
<body>
<jsp:forward page="YourItems" />
<div class="sidenav">
    <a href="#"><%=session.getAttribute("username") %></a>
    <a href="Logout">Logout</a>
    <a href="YourItems.jsp">Your items</a>
    <a href="AddItem.jsp">Add item</a>
    <a href="Search.jsp">Search</a>
</div>


</body>
</html>