<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
    <title>Railway reservation system</title>
    <title>Title</title>
    <style>
        <%@include file='resources/trains.css'%>
    </style>
</head>
<body>

<div>
    <form action="menu.jsp" align="right">
        <button class=button type="submit">Menu</button>
        <br>
    </form>
</div>

<div class="style_gap">
    <h1 align="center" class="font">
        Train number: ${train}
    </h1>
    <br>
    <br>
    <p class="message" align="center">${message}</p>
    <table align="center">
        <tr align="left">
            <th>Origin station</th>
            <th>Destination station</th>
            <th>Number of seats</th>
            <th>Departure date</th>
            <th>Arrival date</th>
            <th></th>
        </tr>

        <tr>
            <form class="addTrip" method="post">
                <input type="hidden" name="train_number" value="${train}"/>
                <td><input type="text" name="origin_station" formmethod="post" placeholder="Origin station"></td>
                <td><input type="text" name="destination_station" formmethod="post" placeholder="Destination station">
                </td>
                <td><input type="number" value="1" min="1" max="100" name="number_of_seats" formmethod="post"
                           placeholder="Number of seats" required></td>
                <td><input type="datetime-local" name="departure_time" placeholder="Departure time" formmethod="post"></td>
                <td><input type="datetime-local" name="arrival_time" placeholder="Arrive time" formmethod="post"></td>
                <td>
                    <button id="submit">add train</button>
                </td>
            </form>
        </tr>

    </table>

    <script>
        $("#addTrip").submit(function(event){
            event.preventDefault();
            var form = $(this);
            var trainNumberVal = form.find('input[name="train_number"]').val();
            var originStationVal = form.find('input[name="origin_station"]').val();
            var destinationStationVal = form.find('input[name="destination_station"]').val();
            var numberOfSeatsVal = form.find('input[name="number_of_seats"]').val();
            var departureTimeVal = form.find('input[name="departure_time"]').val();
            var arrivalTimeVal = form.find('input[name="arrival_time"]').val();
            var url = 'http://localhost:8080/addTrain?';
            var trainDto = JSON.stringify({number: trainNumberVal, originStation: originStationVal,
                destinationStation: destinationStationVal, seats: numberOfSeatsVal, departureTime: departureTimeVal,
                arrivalTime: arrivalTimeVal});
            console.log(trainDto);

            $.ajax({
                type : 'POST',
                url : url,
                contentType: 'application/json',
                data : trainDto,
                success : function(data, status, xhr){
                    /* $("#result").html(data+
                         " link: <a href='"+url+"'>"+url+"</a>");
                 },
                 error: function(xhr, status, error){
                     alert(error);*/
                }
            });
        });
    </script>

    <c:forEach items="${listOfStations}" var="stationsNames">
        <tr>
            <td>${stationsNames}</td>
        </tr>
        <br>
    </c:forEach>
</div>




<%--
<script>
    jQuery(document).ready(function($) {
        $("#submit").click(function(){
            var trainDto = {};
            trainDto["number"] = $("#train_number").val();
            trainDto["originStation"] = $("#origin_station").val();
            trainDto["departureStation"] = $("#destination_station").val();
            trainDto["seats"] = $("#number_of_seats").val();
            trainDto["departureTime"] = $("#departure_time").val();
            trainDto["arrivalTime"] = $("#arrival_time").val();

            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "addTrip",
                data : JSON.stringify(trainDto),
                dataType : 'json',
                success : function(trainDto) {
                    $('#processedData').html(JSON.stringify(trainDto));
                    $('#displayDiv').show();
                }
            });
        });
    });
</script>
--%>

</body>
</html>
