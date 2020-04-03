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
<form action="index.jsp" align="right">
    <button  class=button type="submit">Home</button>
</form>
<div class="style_gap">
    <h1 class="text" align="center">
        ${train.originStation.name} - ${train.destinationStation.name}
    </h1>
    <p align="center">
    ${message}
    </p>
    <table class = table align=center>
        <tr align="left">
            <th class="font">Number</th>
            <th class="font">Departure time</th>
            <th class="font">Arrival time</th>
        </tr>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td align="center">${train.number}</td>
                <td>${train.departureTime}</td>
                <td>${train.arrivalTime}</td>
                <form action="addTicket">
                <td><button>buy a ticket</button></td>
                    <input type="hidden" name="trainNumber" value="${train.number}">
                    <input type="hidden" name="stationA" value="${train.originStation.id}">
                    <input type="hidden" name="stationB" value="${train.destinationStation.id}">
                    <input type="hidden" name="departureTime" value="${train.departureTime}">
                    <input type="hidden" name="arrivalTime" value="${train.arrivalTime}">
                    <input type="hidden" name="timeDeparture" value="${timeDeparture}">
                    <input type="hidden" name="timeArrival" value="${timeArrival}">
                </form>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>
