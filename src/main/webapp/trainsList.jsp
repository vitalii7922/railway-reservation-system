<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.project.dto.TrainDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Railway reservation system</title>
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
        List of trains
    </h1>
    <p align="center" class="message">
        ${message}
    </p>
    <table class = table align=center>
        <tr align="left">
            <th class="th">Number</th>
            <th class="th">From</th>
            <th class="th">Departure time</th>
            <th class="th">To</th>
            <th class="th">Arrival time</th>
        </tr>
        <c:forEach items="${listOfTrains}" var="train">
            <tr>
                <td align="center">${train.number}</td>
                <td>${train.originStation.name}</td>
                <td>${train.departureTime}</td>
                <td>${train.destinationStation.name}</td>
                <td>${train.arrivalTime}</td>
                <form action="getPassengers">
                <td><button>Passengers</button></td>
                <input type="hidden" name="trainNumber" value="${train.number}">
                </form>
                <form action="addTrain">
                    <td><button>Stations</button></td>
                    <input type="hidden" name="train_number" value="${train.number}">
                </form>
                <form action="getSeats">
                    <td><button>Seats</button></td>
                    <input type="hidden" name="train_number" value="${train.number}">
                </form>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
