<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file='resources/style.css'%>
        <%@include file='resources/login.css'%>
    </style>
    <header>
        <h1 class="text" align="center">
            Enter data
        </h1>
    </header>
</head>
<body>
<div class="text" align="center">
<form action="addPassenger">
    <input type="hidden" name="trainNumber" value="${trainNumber}"/>
    <input type="hidden" name="stationA" value="${stationA}"/>
    <input type="hidden" name="stationB" value="${stationB}"/>
    <input type="hidden" name="departureTime" value="${departureTime}"/>
    <input  type="text" name="first_name" formmethod="post" placeholder="First name"><br>
    <input  type="text" name="last_name" formmethod="post" placeholder="Last name"><br>
    <td><input type="date" name="date_of_birth" placeholder="Date of birth"></td><br>
    <button class="button" type="submit">buy</button>
</form>
</div>
</body>
</html>
