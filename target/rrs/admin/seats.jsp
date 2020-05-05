<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='../resources/trains.css'%>
    </style>
</head>
<form action="menu.jsp" align="right">
    <button  class=button type="submit">Menu</button><br>
</form>
    <div class="style_gap">
        <h1 class="font">
            Train number: ${train}
        </h1>
        <table class="table_style_list_trains">
            <caption></caption>
            <tr>
                <th class="font" scope="col">From</th>
                <th class="font" scope="col">To</th>
                <th class="font" scope="col">Free seats</th>
            </tr>
        <jsp:useBean id="trains" scope="request" type="java.util.List"/>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td>${train.originStation}</td>
                <td>${train.destinationStation}</td>
                <td>${train.seats}</td>
            </tr>
        </c:forEach>
    </div>
<body>
</body>
</html>
