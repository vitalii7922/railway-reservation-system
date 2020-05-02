<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Railway reservation system</title>

    <style>
        <%@include file='../resources/trains.css'%>
    </style>
</head>
<body>
<form action="menu.jsp" align="right">
    <button class=button type="submit">Menu</button>
    <br>
</form>

<div align="center">
    <h1 class="font" align="center">
        Train number: ${train}
    </h1>
    <p align="center" class="message">${message}</p>

    <table align="center">
        <tr>
            <th>Destination station</th>
            <th>Number of seats</th>
            <th>Departure time</th>
            <th>Arrival time</th>
        </tr>
        <tr>
                <%--@elvariable id="trainDto" type="com.tsystems.project"--%>
                <form:form method="post" action="trips" modelAttribute="trainDto">
                    <form:errors path="originStation" cssClass="message"/><br>
                    <form:errors path="destinationStation" cssClass="message"/><br>
                    <form:errors path="departureTime" cssClass="message"/><br>
                    <form:errors path="arrivalTime" cssClass="message"/><br>
                    <form:input type="hidden" path="number" value="${train}"/>
                    <td><form:input type="text" placeholder="Destination station" path="destinationStation"/></td>
                    <td><form:input type="number" value="1" min="1" max="100"
                                    placeholder="Number of seats" path="seats" required="number"/></td>
                    <td><form:input type="datetime-local" placeholder="Departure time" path="departureTime"/></td>
                    <td><form:input type="datetime-local" placeholder="Arrive time" path="arrivalTime"/></td>
                    <td>
                        <button type="submit">add train</button>
                    </td>
                </form:form>
            <form action="seats">
                <td>
                    <button>Seats</button>
                </td>
                <input type="hidden" name="train_number" value="${train}">
            </form>
        </tr>
    </table>
</div>
<table align=center>
    <tr>
        <th class="th">Station</th>
        <th class="th">Arrival time</th>
        <th class="th">Departure time</th>
    </tr>
    <c:forEach items="${trainList}" var="train">
        <tr>
            <td>${train.station}</td>
            <td>${train.arrivalTime}</td>
            <td>${train.departureTime}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
