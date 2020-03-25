
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="addTrips">
    <input type="hidden" name="train_number" value="${train}"/>
    Train number: ${train}
    <br>
    <br>
    <c:forEach items="${listOfStations}" var="stationsNames">
        <tr>
            <td>${stationsNames}</td>
        </tr>
        <br>
    </c:forEach>
    <input  type="text" name="destination_station" formmethod="post" placeholder="Destination station">
    <input  type="number" name="number_of_seats" formmethod="post" placeholder="Number of seats">
    <td><input type="datetime-local" name="departure_time" value="2018-06-12T19:30" placeholder="Departure time"></td>
    <td><input type="datetime-local" name="arrival_time" value="2018-06-13T19:30" placeholder="Arrive time"></td>
    <button class="button" type="submit">add train</button>
</form>
</body>
</html>