<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Railway reservation system</title>
    <title>Title</title>
    <style>
        <%@include file='../resources/trains.css'%>
    </style>
</head>
<body>

<div>
    <form action="menu.jsp" align="right">
        <button class=button type="submit">Menu</button>
        <br>
    </form>
</div>

<div class="style_gap" align="center">
    <h1 class="font">
        Train number: ${train}
    </h1>
    <br>
    <br>
    <table class="table_style">
        <caption></caption>
        <tr>
            <th scope="col">Origin station</th>
            <th scope="col">Destination station</th>
            <th scope="col">Number of seats</th>
            <th scope="col">Departure date</th>
            <th scope="col">Arrival date</th>
            <th scope="col"></th>
        </tr>
        <tr>
                <%--@elvariable id="trainDto" type="com.tsystems.project"--%>
                <form:form method="post" action="trip" modelAttribute="trainDto">
                    <form:errors path="originStation" cssClass="message"/><br>
                    <form:errors path="destinationStation" cssClass="message"/><br>
                    <form:errors path="departureTime" cssClass="message"/><br>
                    <form:errors path="arrivalTime" cssClass="message"/><br>
                    <form:input type="hidden" path="number" value="${train}"/>
                    <td><form:input type="text" placeholder="Origin station" path="originStation"/></td>
                    <td><form:input type="text" placeholder="Destination station" path="destinationStation"/></td>
                    <td><form:input type="number" value="1" min="1" max="100"
                                    placeholder="Number of seats" path="seats" required="number"/></td>
                    <td><form:input type="datetime-local" placeholder="Departure time" path="departureTime"/></td>
                    <td><form:input type="datetime-local" placeholder="Arrival time" path="arrivalTime"/></td>
                    <td>
                        <button type="submit">add train</button>
                    </td>
                </form:form>
        </tr>
    </table>
    <c:forEach items="${listOfStations}" var="stationsNames">
    <tr>
        <td>${stationsNames}</td>
    </tr>
    <br>
    </c:forEach>

</body>
</html>
