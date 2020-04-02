<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
   /* if (session.getAttribute("admin") == null) {
        response.sendRedirect("login.jsp");
    }*/
%>
<div class="style_gap">
    <h1 class="text" align="center">
        List of passengers<br>
        Train number: ${train}
    </h1>

    <table class = table align=center>
        <tr align="left">
            <th>First name</th>
            <th>Last name</th>
            <th>Birth date</th>
        </tr>
        <c:forEach items="${passengers}" var="passenger">
            <tr>
                <td>${passenger.firstName}</td>
                <td>${passenger.secondName}</td>
                <td>${passenger.birthDate}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
