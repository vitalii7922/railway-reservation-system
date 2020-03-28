<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file='resources/style.css'%>
        <%@include file='resources/login.css'%>
    </style>
    <header>
        <h1 class="text" align="center">
            Enter personal data
        </h1>
    </header>
</head>
<body>
<div class="text" align="center">
<form action="addPassenger">
    <input type="hidden" name="trainNumber" value="${trainNumber}"/>
    <input type="hidden" name="stationA" value="${stationA}"/>
    <input type="hidden" name="stationB" value="${stationB}"/>
    <input  type="text" name="first_name"  placeholder="First name"><br>
    <input  type="text" name="last_name" placeholder="Last name"><br>
    <input type="date" name="date_of_birth"><br>
    <button class="button" type="submit">buy</button>
</form>
</div>
</body>
</html>
