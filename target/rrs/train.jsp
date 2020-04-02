<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    /*if (session.getAttribute("admin") == null) {
        response.sendRedirect("login.jsp");
    }*/
%>

<div class="style_gap">
    Train number: ${train}
    <br>
    <br>
    <%--<table class = table align=center>
        <tr align="left">
            <th>id</th>
            <th>name</th>
        </tr>--%>
        ${message}
        <c:forEach items="${listOfStations}" var="stationsNames">
            <tr>
                <td>${stationsNames}</td>
            </tr>
            <br>
        </c:forEach>
    <form action="addTrip">
     <input type="hidden" name="train_number" value="${train}"/>
    <input  type="text" name="origin_station" formmethod="post" placeholder="Origin station">
    <input  type="text" name="destination_station" formmethod="post" placeholder="Destination station">
    <input  type="number" value="1" min="1" max="100" name="number_of_seats" formmethod="post" placeholder="Number of seats" required>
    <td><input type="datetime-local" name="departure_time" value="2020-04-01T19:30" placeholder="Departure time"></td>
    <td><input type="datetime-local" name="arrival_time" value="2020-04-02T19:30" placeholder="Arrive time"></td>
    <button class="button" type="submit">add train</button>
    </form>
</div>

</body>
</html>
