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
    <form>
    <table class = table align=center>
        <tr align="left">
            <th>From</th>
            <th>To</th>
            <th>Departure date</th>
            <th>Arrival date</th>
        </tr>
        <tr class=dark>
                <td><input type="text" id="from" name="from" placeholder="From"></td>
                <td><input type="text" id="to" name="to" placeholder="To"></td>
                <td><input type="datetime-local" name=date></td>
                <td><input type="datetime-local" name=date></td>
                <td><button type="button" class="">Find</button></td>
        </tr>
    </table>
    </form>
</div>

</body>
</html>

