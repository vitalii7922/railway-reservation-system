<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.project.dto.TrainDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
   /* if (session.getAttribute("admin") == null) {
        response.sendRedirect("login.jsp");
    }*/
%>
<div class="style_gap">
    <h1 class="text" align="center">
        List of trains
    </h1>
    <p align="center">
        ${message}
    </p>
    <table class = table align=center>
        <tr align="left">
            <th>number</th>
            <th>from</th>
            <th>Departure time</th>
            <th>to</th>
            <th>Arrival time</th>
        </tr>
        <c:forEach items="${listOfTrains}" var="train">
            <tr>
                <td>${train.number}</td>
                <td>${train.originStation.name}</td>
                <td>${train.departureTime}</td>
                <td>${train.destinationStation.name}</td>
                <td>${train.arrivalTime}</td>
                <form action="getPassengers">
                <td><button>Passengers</button></td>
                <input type="hidden" name="trainNumber" value="${train.number}">
                </form>
                <form action="getTrips">
                    <td><button>Stations</button></td>
                    <input type="hidden" name="trainNumber" value="${train.number}">
                </form>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
