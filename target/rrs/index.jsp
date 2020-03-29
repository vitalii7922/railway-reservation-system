<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='resources/style.css'%>
    </style>
</head>
<body>
<header>
    <div class = "line" align = right>
        <a href="account.jsp">
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
    <form action="getTrips">
    <table class = table align=center>
        <tr align="left">
            <th>From</th>
            <th>To</th>
            <th>Departure date</th>
            <th>Arrival date</th>
        </tr>
        <tr class=dark>
                <td><input type="text" name="from" placeholder="From"></td>
                <td><input type="text" name="to" placeholder="To"></td>
                <td><input type="datetime-local" value="2018-06-12T19:30" name=time_departure></td>
                <td><input type="datetime-local" value="2018-06-132T19:30" name=time_arrival></td>
                <td><button type="submit">Find</button></td>
        </tr>
        <p align="center">${message}</p>
    </table>
    </form>
</div>
</body>
</html>

