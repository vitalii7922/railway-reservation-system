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

<div class="style_gap">
    <form action="menu.jsp" align="right">
        <button  class=button type="submit">Menu</button><br>
    </form>
    <h1 align="center">
        Train number: ${train}
    </h1>
    <br>
    <br>
        ${message}
    <table align="center">
        <tr align="left">
            <th>Origin station</th>
            <th>Destination station</th>
            <th>Number of seats</th>
            <th>Departure date</th>
            <th>Arrival date</th>
            <th>                   </th>
        </tr>

        <tr>
            <form action="addTrip">
        <input type="hidden" name="train_number" value="${train}"/>
        <td><input  type="text" name="origin_station" formmethod="post" placeholder="Origin station"></td>
        <td><input  type="text" name="destination_station" formmethod="post" placeholder="Destination station"></td>
        <td><input  type="number" value="1" min="1" max="100" name="number_of_seats" formmethod="post" placeholder="Number of seats" required></td>
        <td><input type="datetime-local" name="departure_time" value="2020-04-01T19:30" placeholder="Departure time"></td>
        <td><input type="datetime-local" name="arrival_time" value="2020-04-02T19:30" placeholder="Arrive time"></td>
                <td><button class="button" type="submit">add train</button></td>
            </form>
        </tr>

        </table>

        <c:forEach items="${listOfStations}" var="stationsNames">
            <tr>
                <td>${stationsNames}</td>
            </tr>
            <br>
        </c:forEach>

</div>

</body>
</html>
