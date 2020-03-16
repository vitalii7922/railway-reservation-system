<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='resources/style.css'%>
    </style>
</head>
<body>
<header>
    <div class = "line" align = center>
        <p>Railway reservation system</p>
    </div>
</header>

<div align = center>
    <p>
        Admin menu
    </p>
</div>
<div align=center>
    <form action="add">
        <label>Add stations</label><br>
        <input type="text" name="station" formmethod="post" placeholder="Station name">
        <p>${message}</p>
        <button type="submit">add</button><br>
    </form>
    <form action="print">
        <button align="center" class=style type="submit">List of stations</button><br>
    </form>
</div>

<div>

<c:forEach items="${listOfParams}" var="station">
    <tr>
        <td>${station.id}</td>
        <td>${station.name}</td>
    </tr>
    <br>
</c:forEach>

</div>

</body>
</html>
