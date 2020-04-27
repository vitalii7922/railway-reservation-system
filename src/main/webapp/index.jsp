<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='resources/style.css'%>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>
<header>
    <div class = "line" align = right>
        <a href="login.jsp">
            Administrator
        </a>
    </div>
</header>
<div class="text"  align = center>
    <h1>
        Railway reservation system
    </h1>
</div>
<div class="text">
    <p align=center>
        Choose stations for a trip:
    </p>
    <form action="trips">
    <table class = table align=center>
        <tr align="left">
            <th>From</th>
            <th>To</th>
            <th>Departure time</th>
            <th>Arrival time</th>
        </tr>
        <tr class=dark>
                <td><input type="text" name="from" placeholder="From"></td>
                <td><input type="text" name="to" placeholder="To"></td>
                <td><input type="datetime-local" name=time_departure required></td>
                <td><input type="datetime-local" name=time_arrival required></td>
                <td><button type="submit">Find</button></td>
        </tr>
        <p align="center" class="message">${message}</p>
    </table>
    </form>
    <div>
        <form action="stations-all" align="center">
            <button  class=button_index type="submit">List of stations</button><br>
        </form>
    </div>
</div>
</body>
</html>

