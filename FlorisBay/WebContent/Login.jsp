<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/LoginStyle.css">
<title>Login</title>
</head>

<body>
<div class="login-page">
	<form class="form" action="Login" method="post">
        <input type="text" name="username" placeholder="username" required/>
        <input type="password" name="password" placeholder="password" required/>
        <button>login</button>
        <% if (request.getParameter("login_state") != null){%>
            <p class="error_message"><%=request.getParameter("login_state")%></p>
            <%} %>

        <p class="message">Not registered? <a href="CreateUser.jsp">Create an account</a></p>
	</form>
</div>
</body>
</html>