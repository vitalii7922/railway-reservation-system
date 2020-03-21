
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="style_gap">
    <h1 class="text" align="center">
        List of trains
    </h1>
    <table class = table align=center>
        <tr align="left">
            <th>number</th>
            <th>from</th>
            <th>to</th>
        </tr>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td>${train.number}</td>
                <td>${train.originStation.name}</td>
                <td>${train.destinationStation.name}</td>
                <td><button>Open</button></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
