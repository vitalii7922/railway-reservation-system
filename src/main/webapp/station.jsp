<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>

<form action="index.jsp" align="right">
    <button  class=button type="submit">Home</button>
</form>

<form action="menu.jsp" align="right">
    <button  class=button type="submit">Menu</button><br>
</form>

<h1 align="center" class="font"> List of stations </h1>

<div align="center" class="style_gap">
    <p class="message">${message}</p>
        <table align=center>
            <tr align="left">
                <th class="th">Name</th>
                <th></th>
            </tr>
            <c:forEach items="${listOfParams}" var="station">
            <tr>
                <td>${station.name}</td>
                <form action="getSchedules">
                    <td><button class="button_station">Schedule</button></td>
                    <input type="hidden" name="stationId" value="${station.id}">
                </form>
            </tr>
            </c:forEach>
        </table>
</div>
</body>
</html>
