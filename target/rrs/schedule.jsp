<jsp:useBean id="schedule" scope="request" type="com.tsystems.project.dto.ScheduleDto"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Railway reservation system</title>
    <title>Title</title>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>
<div>
    <form action="index.jsp" align="right">
        <button  class=button type="submit">Home</button>
    </form>

    <form action="stations-all" align="right">
        <button  class=button type="submit">Stations</button><br>
    </form>
</div>
<div>
        <h1 class="font">
            ${schedule.stationName}
        </h1>
        <table class = "table_style">
            <caption></caption>
            <tr style="text-align: left">
                <th class="th" scope="col">Number</th>
                <th class="th" scope="col">Arrival time</th>
                <th class="th" scope="col">Departure time</th>
            </tr>
            <jsp:useBean id="schedules" scope="request" type="java.util.List"/>
            <c:forEach items="${schedules}" var="schedule">
                <tr>
                    <td class="td_padding" style="text-align: center">${schedule.trainNumber}</td>
                    <td class="td_padding">${schedule.arrivalTime}</td>
                    <td class="td_padding">${schedule.departureTime}</td>
                </tr>
            </c:forEach>
        </table>
</div>
</body>
</html>
