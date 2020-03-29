<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="style_gap">
    <h1 class="text" align="center">
        ${train.originStation.name} - ${train.destinationStation.name}
    </h1>
    <form action="/addTicket">
    <table class = table align=center>
        <tr align="left">
            <th>number</th>
            <th>Departure time</th>
            <th>Arrival time</th>
        </tr>
<%--        <form action="buyTicket">--%>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td>${train.number}</td>
                <td>${train.departureTime}</td>
                <td>${train.arrivalTime}</td>
                <td><button>buy a ticket</button></td>
            </tr>

            <input type="hidden" name="trainNumber" value="${train.number}">
            <input type="hidden" name="stationA" value="${train.originStation.id}">
            <input type="hidden" name="stationB" value="${train.destinationStation.id}">
            <input type="hidden" name="departureTime" value="${train.departureTime}">

        </c:forEach>

    </table>
    </form>>
   <%-- <script type="text/javascript">
        document.getElementById("myButton").onclick = function () {
            location.href = "buyTicket?trainNumber=2&stationA=1&stationB=2";
        };
    </script>--%>
</div>
</body>
</html>
