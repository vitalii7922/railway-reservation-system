<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<form action="menu.jsp" align="right">
    <button  class=button type="submit">Menu</button><br>
</form>
    <div class="style_gap">
        <h1 align="center">
            Train number: ${train}
        </h1>
        </table>
        <table align="center">
            <tr align="left">
                <th class="font">From</th>
                <th class="font">To</th>
                <th class="font">Free seats</th>
            </tr>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td>${train.originStation.name}</td>
                <td>${train.destinationStation.name}</td>
                <td align="center">${train.seats}</td>
            </tr>
            <br>
        </c:forEach>
    </div>
<body>

</body>
</html>
