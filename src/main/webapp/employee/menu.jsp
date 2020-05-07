<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='../resources/menu.css'%>
        <%@include file='../resources/trains.css'%>
    </style>
</head>
<body>
<header class="text" align=center>
    <div>
        <c:url value="/j_spring_security_logout" var="logoutUrl"/>
        <form action="${logoutUrl}" method="get" align="right">
            <button class=button type="submit">Logout</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <form action="../index.jsp" align="right">
            <button class=button type="submit">Home</button>
        </form>
    </div>
    <h1 class="font">
        Menu
    </h1>
    <hr>
</header>
<div>
    <form action="station" method="post" align="center">
        <label class="th">Add stations</label><br>
        <label>
            <input class="input" type="text" name="station" placeholder="Station name">
        </label>
        <button class="button" type="submit">add</button>
        <p class="message">${messageStation}</p>
    </form>
</div>
<hr>
<div class=style_gap>
    <form action="train" method="post" align="center">
        <label class="th">Add trains</label><br>
        <label>
            <input class="input" min="1" max="1 000 000" type="number" name="train_number" placeholder="Train number"
                   required>
        </label>
        <button class="button" type="submit">add</button>
        <p class="message">${messageTrain}</p>
    </form>
    <form action="trains" align="center">
        <button class=button type="submit">List of trains</button>
        <br>
        <p>${messageList}</p>
    </form>
</div>
</body>
</html>
