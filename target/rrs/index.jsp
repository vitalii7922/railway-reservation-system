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
<div align = center>
    <p>
        Railway reservation system
    </p>
</div>
<div>
    <p align=center>
        Choose stations for a trip:
    </p>
    <form>
    <table class = table align=center style="width:100px">
        <tr>
            <th>From</th>
            <th>To</th>
            <th>Departure date</th>
            <th>Arrival date</th>
        </tr>
        <tr class=input>
                <td><input type="text" id="from" name="from"></td>
                <td><input type="text" id="to" name="to"></td>
                <td><input type="datetime-local" name=date></td>
                <td><input type="datetime-local" name=date></td>
                <td><button type="button">Find</button></td>
        </tr>
    </table>
    </form>
</div>

</body>
</html>

