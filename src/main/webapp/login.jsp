<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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

<form action="login" method="post">
    <div class="text" align="center">
        <p>${message}</p>
        <label><b>Username</b></label><br>
        <input type="text" placeholder="Enter Username" name="login" required><br>

        <label><b>Password</b></label><br>
        <input type="password" placeholder="Enter Password" name="password" required><br>

        <button type="submit">Login</button>
       <%-- <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />--%>
    </div>
</form>

</body>
</html>
