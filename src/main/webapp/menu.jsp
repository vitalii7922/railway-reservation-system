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
    <form action="addStation">
        <label>Add stations</label><br>
        <input type="text" name="station" placeholder="Station name">
        <button class="button" type="submit">add</button>
        <p>${messageStation}</p>
    </form>
    <form action="getStations">
        <button  class=button type="submit">List of stations</button><br>
    </form>
</div>

<hr>

<div align="center" class=style_gap>
    <form action="addTrain">
        <label>Add trains</label><br>
        <input  type="number" name="train_number" placeholder="Train number">

        <button class="button" type="submit">add</button>
        <p>${messageTrain}</p>
    </form>
    <form action="getTrains">
        <button  class=button type="submit">List of trains</button><br>
    </form>
</div>

</body>
</html>
