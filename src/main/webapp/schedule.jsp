<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>

    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>
<%--<form action="menu.jsp" align="right">
    <button  class=button type="submit">Menu</button><br>
</form>--%>
<div class="style_gap">

        <h1 class="text" align="center">
            ${schedule.station.name}
        </h1>
        <table class = table align=center>
            <tr align="left">
                <th class="font">Train number</th>
                <th class="font">Arrival time</th>
                <th class="font">Departure time</th>
            </tr>
            <c:forEach items="${schedules}" var="schedule">
                <tr>
                    <td align="center">${schedule.train.number}</td>
                    <td>${schedule.arrivalTime}</td>
                    <td>${schedule.departureTime}</td>
                </tr>
            </c:forEach>
        </table>
</div>
</body>
</html>
