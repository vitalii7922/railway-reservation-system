<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="style_gap">
    <h1 class="text" align="center">
        ${train.originStation.name} - ${train.destinationStation.name}
    </h1>

    <table class = table align=center>
        <tr align="left">
            <th>number</th>
            <th>Departure time</th>
            <th>Arrival time</th>
        </tr>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td>${train.number}</td>
                <td>${train.departureTime}</td>
                <td>${train.arrivalTime}</td>
                <td><button id="myButton">buy a ticket</button></td>
            </tr>
        </c:forEach>
    </table>
    <script type="text/javascript">
        document.getElementById("myButton").onclick = function () {
            location.href = "buyTicket?train=${train}&stationA=${train.originStation}&stationB=${train.destinationStation}";
        };
    </script>
</div>
</body>
</html>
