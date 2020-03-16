<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file='resources/style.css'%>
        <%@include file='resources/login.css'%>
    </style>
    <h1 align="center"> List of stations </h1>
</head>
<body>
<div class="style_gap">

        <table class = table align=center>
            <tr align="left">
                <th>id</th>
                <th>name</th>
            </tr>
            <c:forEach items="${listOfParams}" var="station">
            <tr>
                <td>${station.id}</td>
                <td>${station.name}</td>
            </tr>
            </c:forEach>
        </table>

</div>
</body>
</html>
