<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
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
    <h1 class="font" align="center">
        ${train.originStation} - ${train.destinationStation}
    </h1>
    <p class="message" align="center">
    ${message}
    </p>
    <form:errors path="trainDto.seats" cssClass="message"/><br>
    <form:errors path="trainDto.departureTime" cssClass="message"/><br>
    <table class = table align=center>
        <tr align="left">
            <th class="th">Number</th>
            <th class="th">Departure time</th>
            <th class="th">Arrival time</th>
        </tr>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td align="center">${train.number}</td>
                <td>${train.departureTime}</td>
                <td>${train.arrivalTime}</td>
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
