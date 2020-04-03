<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket</title>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>
<div>
<form action="index.jsp" align="right">
    <button  class=button type="submit">Home</button>
</form>
</div>

<div class="font" align="center">
    Ticket number: ${ticket.id}<br>
    Train number: ${ticket.trainNumber}<br>
    Origin station: ${ticket.stationOrigin}<br>
    Departure time: ${ticket.departureTime}<br>
    Destination station: ${ticket.stationDeparture}<br>
    Arrival time: ${ticket.arrivalTime}<br>
    First name: ${ticket.firstName}<br>
    Last name: ${ticket.lastName}<br>
    Birth date: ${ticket.birthDate}<br>
</div>
</body>
</html>
