<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Railway reservation system</title>
    <title>Title</title>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>
<div>
    <form action="index.jsp" align="right">
        <button  class=button type="submit">Home</button>
    </form>

    <form action="menu.jsp" align="right">
        <button  class=button type="submit">Menu</button><br>
    </form>
</div>
<div class="style_gap">
        <h1 class="font" align="center">
            ${schedule.stationName}
        </h1>
        <table class = table align=center>
            <tr align="left">
                <th class="th">Train number</th>
                <th class="th">Arrival time</th>
                <th class="th">Departure time</th>
            </tr>
            <c:forEach items="${schedules}" var="schedule">
                <tr>
                    <td align="center">${schedule.trainNumber}</td>
                    <td>${schedule.arrivalTime}</td>
                    <td>${schedule.departureTime}</td>
                </tr>
            </c:forEach>
        </table>
</div>
</body>
</html>
