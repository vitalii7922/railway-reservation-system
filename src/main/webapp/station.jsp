<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
    <h1 align="center" class="font"> List of stations </h1>
</head>
<body>

<div align="center" class="style_gap">
    <p class="message">${message}</p>
        <table align=center>
            <tr align="left">
                <th class="th">Name</th>
                <th>                             </th>
            </tr>
            <c:forEach items="${listOfParams}" var="station">
            <tr>
                <td>${station.name}</td>
                <form action="getSchedules">
                    <td><button class="button_station">Schedule</button></td>
                    <input type="hidden" name="stationId" value="${station.id}">
                </form>
<%--                <td><a href="getSchedules?stationId=${station.id}">Schedule</a></td>--%>
            </tr>
            </c:forEach>
        </table>
</div>
</body>
</html>
