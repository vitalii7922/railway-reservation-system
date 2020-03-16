<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Railway reservation system</title>
    <style>
        <%@include file='resources/style.css'%>
        <%@include file='resources/login.css'%>
    </style>
</head>
<body>
<header class="text" align=center>
    <h1>
    Admin menu
    </h1>
    <hr>
</header>
<div align=center class="style_gap">
    <form action="add_stations">
        <label>Add stations</label><br>
        <input type="text" name="station" formmethod="post" placeholder="Station name">
        <button class="button" type="submit">add</button>
        <p>${messageStation}</p>
    </form>
    <form action="get_stations">
        <button  class=button type="submit">List of stations</button><br>
    </form>
</div>

<hr>

<div align="center" class=style_gap>
    <form action="add_trains">
        <label>Add trains</label><br>
        <input  type="text" name="train_number" formmethod="post" placeholder="Train number">
        <input  type="text" name="origin_station" formmethod="post" placeholder="Origin station">
        <input  type="text" name="destination_station" formmethod="post" placeholder="Destination station">
        <input  type="text" name="number_of_seats" formmethod="post" placeholder="Number of seats">
        <button class="button" type="submit">add</button>
        <p>${messageTrain}</p>
    </form>
    <form action="get">
        <button  class=button type="submit">List of trains</button><br>
    </form>
</div>
<%--<a href="train.jsp">train</a>--%>

</body>
</html>
