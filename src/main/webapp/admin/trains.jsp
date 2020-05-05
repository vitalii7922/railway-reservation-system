<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
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
    <h1 class="font">
        Train number: ${train}
    </h1>
    <p class="message">${message}</p>

    <table class="table_style">
        <caption></caption>
        <tr>
            <th scope="col">Destination station</th>
            <th scope="col">Number of seats</th>
            <th scope="col">Departure time</th>
            <th scope="col">Arrival time</th>
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
<table class="table_style_list_trains">
    <caption></caption>
    <tr>
        <th class="th" scope="col">Station</th>
        <th class="th" scope="col">Arrival time</th>
        <th class="th" scope="col">Departure time</th>
    </tr>
    <jsp:useBean id="trainList" scope="request" type="java.util.List"/>
    <c:forEach items="${trainList}" var="train">
        <tr>
            <td class="td_padding">${train.station}</td>
            <td class="td_padding">${train.arrivalTime}</td>
            <td class="td_padding">${train.departureTime}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
