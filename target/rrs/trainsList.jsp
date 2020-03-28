<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.project.dto.TrainDto" %>
<%@ page import="com.tsystems.project.domain.Train" %>
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
            <th>Departure time</th>
            <th>to</th>
            <th>Arrival time</th>
        </tr>
        <%
            List<TrainDto> trainDtos = (List<TrainDto>) request.getAttribute("trains");
            for(TrainDto t : trainDtos){%>
        <tr>
            <td><%=t.getNumber()%></td>
            <td><%=t.getOriginStation().getName()%></td>
            <td><%=t.getDepartureTime()%></td>
            <td><%=t.getDestinationStation().getName()%></td>
            <td><%=t.getArrivalTime()%></td>
        </tr>
        <%}%>
    </table>
</div>
</body>
</html>
