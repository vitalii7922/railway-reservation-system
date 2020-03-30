<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="style_gap">
    <h1 class="text" align="center">
        ${station}
    </h1>
    <table class = table align=center>
        <tr align="left">
            <th>Train number</th>
            <th>Arrival time</th>
            <th>Departure time</th>
        </tr>
        <c:forEach items="${schedules}" var="schedule">
            <tr>
                <td>${schedule.train.number}</td>
                <td>${schedule.arrivalTime}</td>
                <td>${schedule.departureTime}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
