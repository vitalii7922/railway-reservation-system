<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.project.dto.TrainDto" %>
<%@ page import="java.time.LocalDateTime" %>
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
<form action="menu.jsp" align="right">
    <button class=button type="submit">Menu</button><br>
</form>

    <h1 align="center">
    Train number: ${train}
    </h1>
    <p align="center">${message}</p>

        <table align="center">

            <tr align="left">
                <th>Destination station</th>
                <th>Number of seats</th>
                <th>Departure time</th>
                <th>Arrival time</th>
                <th>                   </th>
            </tr>
            <tr>
                <form action="addTrips">
                <input type="hidden" type="number" name="train_number" value="${train}"/>
                <td> <input  type="text" name="destination_station" formmethod="post" placeholder="Destination station"></td>
                <td><input  type="number" value="1" min="1" max="100" name="number_of_seats" placeholder="Number of seats" required></td>
                <td><input type="datetime-local" name="departure_time" value="2020-04-01T19:30" placeholder="Departure time"></td>
                <td><input type="datetime-local" name="arrival_time" value="2020-04-02T19:30" placeholder="Arrive time"></td>
                <td><button class="button" type="submit">add train</button></td>
            </form>
                <form action="getSeats">
                    <td><button>Seats</button></td>
                    <input type="hidden" name="train_number" value="${train}">
                </form>
            </tr>
</table>
    <table align=center>
        <tr align="left">
            <th class="th">Station</th>
            <th class="th">Arrival time</th>
            <th class="th">Departure time</th>
        </tr>
        <c:forEach items="${listOfStations}" var="train">
            <tr>
                <td>${train.station.name}</td>
                <td>${train.arrivalTime}</td>
                <td>${train.departureTime}</td>
            </tr>
            <br>
        </c:forEach>
    </table>
</body>
</html>
