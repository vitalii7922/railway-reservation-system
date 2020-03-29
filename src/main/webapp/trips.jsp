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
        <form action="passenger.jsp">
        <c:forEach items="${trains}" var="train">
            <tr>
                <td>${train.number}</td>
                <td>${train.departureTime}</td>
                <td>${train.arrivalTime}</td>
                <td><button id="myButton">buy a ticket</button></td>
            </tr>
            <input type="hidden" name="train" value="${train.number}">
        </c:forEach>
    </table>
    <%--<script type="text/javascript">
        document.getElementById("myButton").onclick = function () {
            location.href = "passenger.jsp";
        };
    </script>--%>
</div>
</body>
</html>
