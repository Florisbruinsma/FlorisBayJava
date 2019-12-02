<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/LoginStyle.css">
<title>Create Account</title>
</head>

<body>
<div class="login-page">
	<form class="form" action="CreateUser" method="post">
        <input type="text" name="username" placeholder="username" required/>
        <input type="password" name="password"  placeholder="password" required/>
        <input type="password" name="password2"  placeholder="confirm password" required/>
        <input type="text"  name="email" placeholder="email address" required/>
        <button>create</button>
        <% if (request.getParameter("create_state") != null){%>
            <p class="error_message"><%=request.getParameter("create_state")%></p>
            <%} %>
        <p class="message">Already registered? <a href="Login.jsp">Sign In</a></p>
	</form>
</div>
</body>
</html>