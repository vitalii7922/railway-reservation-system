<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='../resources/menu.css'%>
        <%@include file='../resources/trains.css'%>
    </style>
</head>
<body>
<header class="text" align=center>
    <div align="right">
            <c:url value="/j_spring_security_logout" var="logoutUrl"/>
            <form action="${logoutUrl}" method="get">
                <button class=button type="submit">Logout</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        <form action="../index.jsp">
            <button class=button type="submit">Home</button>
        </form>
    </div>
    <h1 class="font">
        Admin menu
    </h1>
    <hr>
</header>
<div align=center>
        <form action="station" method="post">
            <label class="th">Add stations</label><br>
            <input class="input" type="text" name="station" placeholder="Station name">
            <button class="button" type="submit">add</button>
            <p class="message">${messageStation}</p>
        </form>
       <%-- <form action="stations-all">
            <button class=button type="submit">List of stations</button>
            <br>
        </form>--%>
</div>
<hr>
<div align="center" class=style_gap>
        <form action="train" method="post">
            <label class="th">Add trains</label><br>
            <input class="input" min="1" max="1 000 000" type="number" name="train_number" placeholder="Train number"
                   required>

            <button class="button" type="submit">add</button>
            <p class="message">${messageTrain}</p>
        </form>
        <form action="trains">
            <button class=button type="submit">List of trains</button>
            <br>
            <p>${messageList}</p>
        </form>
</div>
</body>
</html>
