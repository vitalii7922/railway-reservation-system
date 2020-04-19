<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Railway reservation system</title>
    <title>Title</title>
    <style>
        <%@include file='resources/style.css'%>
        <%@include file='resources/login.css'%>
    </style>

</head>
<body>
<form name="myForm" action="index.jsp" align="right">
    <button  class=button type="submit">Home</button><br>
</form>
<header>
    <h1 class="text" align="center">
        Enter personal data
    </h1>
</header>
<div class="text" align="center">
    <p class="message">${message}</p>
<%--@elvariable id="passengerTrainDto" type="com.tsystems.project"--%>
<form:form action="addTicket" method="post" modelAttribute="passengerTrainDto">
    <form:errors path="seats" cssClass="message"/><br>
    <form:errors path="departureTime" cssClass="message"/><br>
    <form:errors path="trainNumber" cssClass="message"/><br>
    <form:input type="hidden" value="${train.number}" path="trainNumber"/>
    <form:input type="hidden" value="${train.originStation}" path="originStation"/>
    <form:input type="hidden" value="${train.destinationStation}" path="destinationStation"/>
    <form:input type="hidden" value="${train.departureTime}" path="departureTime"/>
    <form:input type="hidden" value="${train.seats}" path="seats"/>
    <form:input  type="text"  placeholder="First name" path="firstName"/><br>
    <form:errors path="firstName" cssClass="message"/><br>
    <form:input  type="text" placeholder="Last name" path="secondName"/><br>
    <form:errors path="secondName" cssClass="message"/><br>
    <form:input type="date" path="birthDate"/><br>
    <form:errors path="birthDate" cssClass="message"/><br>
    <button class="button" type="submit">buy</button>
</form:form>
</div>
</body>
</html>
