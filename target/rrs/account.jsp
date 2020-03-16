<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Administrator</title>
    <style>
        <%@include file='resources/login.css'%>
    </style>
</head>
<body>
<form action="login" method="post">
    <div class="container" style="text-align:center;>
    <label for="uname"><b>Username</b></label><br>
    <input type="text" placeholder="Enter Username" name="uname" required><br>

    <label><b>Password</b></label><br>
    <input type="password" placeholder="Enter Password" name="psw" required><br>

    <button type="submit">Login</button>
    </div>
</form>

</body>
</html>
