<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    <button class=button type="submit">Home</button>
</form>

<%--<form action="admin/menu.jsp" align="right">
    <button  class=button type="submit">Menu</button><br>
</form>--%>

<h1 class="font"> List of stations </h1>
<div class="style_gap">
    <p class="message">${message}</p>
    <table class="table_style">
        <caption></caption>
        <tr>
            <th class="th" scope="col">Name</th>
            <th scope="col"></th>
        </tr>
        <c:forEach items="${listOfParams}" var="station">
            <tr>
                <td>${station.name}</td>
                <form action="schedules">
                    <td>
                        <button class="button_station">Schedule</button>
                    </td>
                    <input type="hidden" name="stationId" value="${station.id}">
                </form>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
