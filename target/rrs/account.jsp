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

<form action="login">
    <div class="text" align="center">
        <label><b>Username</b></label><br>
        <input type="text" placeholder="Enter Username" name="uname" required><br>

        <label><b>Password</b></label><br>
        <input type="password" placeholder="Enter Password" name="psw" required><br>

        <button type="submit">Login</button>
    </div>
</form>

</body>
</html>
