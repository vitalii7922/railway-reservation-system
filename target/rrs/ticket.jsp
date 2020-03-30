
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ticket</title>
</head>
<body>

<div class="style_gap" align="center">
    Ticket number: ${ticket.id}<br>
    Train number: ${ticket.trainNumber}<br>

     ${ticket.stationOrigin} - ${ticket.stationDeparture}<br>
     ${ticket.departureTime}   ${ticket.arrivalTime}<br>
     Passenger data:<br>
    ${ticket.firstName}<br>
    ${ticket.lastName}<br>
    ${ticket.birthDate}<br>
</div>
</body>
</html>
