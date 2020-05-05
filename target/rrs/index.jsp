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
    <div class = "line">
        <a href="login.jsp">
            Administrator
        </a>
    </div>
</header>
<div class="text" >
    <h1>
        Railway reservation system
    </h1>
</div>
<div class="text">
    <p style = "text-align: center;">
        Choose stations for a trip:
    </p>
    <form action="trips">
    <table class = "table">
        <caption></caption>
        <tr class="text-align: center;">
            <th scope="col">From</th>
            <th scope="col">To</th>
            <th scope="col">Departure time</th>
            <th scope="col">Arrival time</th>
        </tr>
        <tr class=dark>
                <td><label>
                    <input type="text" name="from" placeholder="From">
                </label></td>
                <td><label>
                    <input type="text" name="to" placeholder="To">
                </label></td>
                <td><label>
                    <input type="datetime-local" name=time_departure required>
                </label></td>
                <td><label>
                    <input type="datetime-local" name=time_arrival required>
                </label></td>
                <td><button type="submit">Find</button></td>
        </tr>
        <p class="message">${message}</p>
    </table>
    </form>
    <div>
        <form action="stations-all" align="center">
            <button  class=button_index type="submit">List of stations</button><br>
        </form>
        <p class="message">${messageStation}</p>
    </div>
</div>
</body>
</html>

