<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='resources/style.css'%>
        <%@include file='resources/login.css'%>
    </style>
</head>
<body>
<header>
    <h1 class="text" align="center">
        Login
    </h1>
</header>
<c:url value="/j_spring_security_check" var="loginUrl"/>
<form action="${loginUrl}"  method="post">
    <div class="text" align="center">
        <p class="message">${message}</p>
        <label><strong>Username</strong></label><br>
        <label>
            <input type="text" placeholder="Enter Username" name="username" required>
        </label><br>

        <label><strong>Password</strong></label><br>
        <label>
            <input type="password" placeholder="Enter Password" name="password" required>
        </label><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit">Login</button>
    </div>
</form>
</body>
</html>
