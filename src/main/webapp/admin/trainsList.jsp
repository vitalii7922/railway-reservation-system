<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.project.dto.TrainDto" %>
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
<div>
    <h1 class="font">
        List of trains
    </h1>
    <p class="message">
        ${message}
    </p>
    <table class="table_style">
        <caption></caption>
        <tr>
            <th class="th" scope="col">Number</th>
            <th class="th" scope="col">From</th>
            <th class="th" scope="col">Departure time</th>
            <th class="th" scope="col">To</th>
            <th class="th" scope="col">Arrival time</th>
        </tr>
        <jsp:useBean id="listOfTrains" scope="request" type="java.util.List"/>
        <c:forEach items="${listOfTrains}" var="train">
            <tr>
                <td class="td_padding">${train.number}</td>
                <td class="td_padding">${train.originStation}</td>
                <td class="td_padding">${train.departureTime}</td>
                <td class="td_padding">${train.destinationStation}</td>
                <td class="td_padding">${train.arrivalTime}</td>
                <form action="passengers">
                    <td>
                        <button>Passengers</button>
                    </td>
                    <input type="hidden" name="trainNumber" value="${train.number}">
                </form>
                <form action="train" method="post">
                    <td>
                        <button>Stations</button>
                    </td>
                    <input type="hidden" name="train_number" value="${train.number}">
                </form>
                <form action="seats">
                    <td>
                        <button>Seats</button>
                    </td>
                    <input type="hidden" name="train_number" value="${train.number}">
                </form>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
