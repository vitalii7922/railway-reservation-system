<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.project.dto.TrainDto" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<form action="addTrips">

    <p align="center">
    Train number: ${train}
    </p>
   <%-- <br>
    <br>
    <c:forEach items="${listOfStations}" var="stationsNames">
        <tr>
            <td>${stationsNames}</td>
        </tr>
        <br>
    </c:forEach>--%>
    <p align="center">${message}</p>
    <table class = table align=center>
        <tr align="left">
            <th>Station</th>
            <th>Departure time</th>
            <th>Arrival time</th>
        </tr>
    <%
        List<TrainDto> trains = (List<TrainDto>) request.getAttribute("listOfStations");

        LocalDateTime arrivalTime = null;%>
       <% for(int i = 0; i < trains.size(); i++) {
        if (trains.size() == 1) {%>
        <tr>
            <td><%=trains.get(i).getOriginStation().getName()%></td>
            <td><%=trains.get(i).getDepartureTime()%></td>
            <td></td>
        </tr>
        <tr>
            <td><%=trains.get(i).getDestinationStation().getName()%></td>
            <td></td>
            <td><%=trains.get(i).getArrivalTime()%></td>
        </tr>
        <%continue;
        } else if (i == trains.size() - 1) {%>
        <tr>
            <td><%=trains.get(i).getOriginStation().getName()%></td>
            <td><%=trains.get(i).getDepartureTime()%></td>
            <td><%=arrivalTime%></td>
        </tr>
        <tr>
            <td><%=trains.get(i).getDestinationStation().getName()%></td>
            <td></td>
            <td><%=trains.get(i).getArrivalTime()%></td>
        </tr>
            <%} else {%>
        <tr>
            <td><%=trains.get(i).getOriginStation().getName()%></td>
            <td><%=trains.get(i).getDepartureTime()%></td>
            <%if (arrivalTime != null) {%>
            <td><%=arrivalTime%></td>
            <%}%>
            <%arrivalTime=trains.get(i).getArrivalTime();%>
        </tr>
        <%}}%>

    </table>
    <input type="hidden" type="number" name="train_number" value="${train}"/>
    <input  type="text" name="destination_station" formmethod="post" placeholder="Destination station">
    <input  type="number" value="1" min="1" max="100" name="number_of_seats" placeholder="Number of seats" required>
    <td><input type="datetime-local" name="departure_time" value="2020-04-01T19:30" placeholder="Departure time"></td>
    <td><input type="datetime-local" name="arrival_time" value="2020-04-02T19:30" placeholder="Arrive time"></td>
    <button class="button" type="submit">add train</button>

</form>
</body>
</html>
