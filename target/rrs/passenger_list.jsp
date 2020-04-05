<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>

<form action="menu.jsp" align="right">
    <button  class=button type="submit">Menu</button><br>
</form>
<div class="style_gap">
    <h1 class="font" align="center">
        List of passengers<br>
        Train number: ${train}
    </h1>

    <table class = table align=center>
        <tr align="left">
            <th class="th">First name</th>
            <th class="th">Last name</th>
            <th class="th">Birth date</th>
        </tr>
        <c:forEach items="${passengers}" var="passenger">
            <tr>
                <td>${passenger.firstName}</td>
                <td>${passenger.secondName}</td>
                <td>${passenger.birthDate}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
