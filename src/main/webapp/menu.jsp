<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Railway reservation system</title>
    <style>
                <%@include file='resources/menu.css'%>
    </style>

</head>
<body>
<header class="text" align=center>
    <div align="right">
        <form action="logout">
            <button  class=button type="submit">Logout</button>
        </form>
        <form action="index.jsp">
            <button  class=button type="submit">Home</button>
        </form>
    </div>
    <h1 class="font">
    Admin menu
    </h1>
    <hr>
</header>

<div align=center>
    <form action="addStation">
        <label class="font">Add stations</label><br>
        <input class="input" type="text" name="station" placeholder="Station name">
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
        <label class="font">Add trains</label><br>
        <input class="input" min="1" type="number" name="train_number" placeholder="Train number" required>

        <button class="button" type="submit">add</button>
        <p>${messageTrain}</p>
    </form>
    <form action="getTrains">
        <button  class=button type="submit">List of trains</button><br>
        <p>${messageList}</p>
    </form>
</div>

</body>
</html>
