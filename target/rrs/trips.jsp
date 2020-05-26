<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>
<form action="index.jsp" align="right">
    <button  class=button type="submit">Home</button>
</form>
<div class="style_gap" align="center">
    <h1 class="font">
        ${train.originStation} - ${train.destinationStation}
    </h1>
    <p class="message">
    ${message}
    </p>
    <form:errors path="trainDto.seats" cssClass="message"/><br>
    <form:errors path="trainDto.departureTime" cssClass="message"/><br>
    <table class = "table_style_list_trains">
        <caption></caption>
        <tr>
            <th class="th" scope="col">Number</th>
            <th class="th" scope="col">Departure time</th>
            <th class="th" scope="col">Arrival time</th>
        </tr>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td class="td_padding">${train.number}</td>
                <td class="td_padding">${train.departureTime}</td>
                <td class="td_padding">${train.arrivalTime}</td>
                <%--@elvariable id="trainDto" type="com.tsystems.project"--%>
                <form:form action="train-verification" modelAttribute="trainDto" method="get">
                    <form:input type="hidden" path="number" value="${train.number}"/>
                    <form:input type="hidden" path="destinationStation" value="${train.destinationStation}"/>
                    <form:input type="hidden" path="originStation" value="${train.originStation}"/>
                    <form:input type="hidden" path="departureTime" value="${train.departureTime}"/>
                    <form:input type="hidden" path="arrivalTime" value="${train.arrivalTime}"/>
                    <form:input type="hidden" path="seats" value="${train.seats}"/>
                    <form:input type="hidden" path="allTrainsDepartureTime" value="${timeDeparture}"/>
                    <form:input type="hidden" path="allTrainsArrivalTime" value="${timeArrival}"/>
                    <td><button type="submit">buy a ticket</button></td>
                </form:form>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
