
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="style_gap">
    <h1 class="text" align="center">
        ${stationA} - ${stationB}
    </h1>

    <table class = table align=center>
        <tr align="left">
            <th>number</th>
            <th>Departure time</th>
            <th>Arrival time</th>
        </tr>
        <c:forEach items="${schedules}" var="mapElement">
            <tr>
                <td>${mapElement.key.train.number}</td>
                <td>${mapElement.key.departureTime}</td>
                <td>${mapElement.value.arrivalTime}</td>
                <td><button id="myButton">buy a ticket</button></td>
            </tr>
        </c:forEach>
    </table>
    <script type="text/javascript">
        document.getElementById("myButton").onclick = function () {
            location.href = "buyTicket?stationA=${stationA}&stationB=${stationB}&trainNumber${mapElement.key.train.number}&departureTime${mapElement.key.departureTime}";
        };
    </script>
</div>
</body>
</html>
