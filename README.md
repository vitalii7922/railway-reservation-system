## Description

Railway reservation system provides: 
-	Services for clients, such as look for trains according to a rout from point A to point B at a given term, book tickets for chosen trains and view a schedule of trains in a chosen station.
-	Services for staff: add stations, add rout of trains, view passengers that are registered on a train and view all trains.

---
## List of used technologies

-	Maven
-	Hibernate
-	Spring framework, EJB
-	MySQL
-	JSP, JSF
-	Tomcat, WildFly
-	ActiveMQ

---
## DB schema
![Image description](https://github.com/vitalii7922/railway-reservation-system/blob/refactoring/src/main/java/com/tsystems/project/db/db.jpg)

---
Used css files in applications: login.css, menu.css, trains.css, style.css, screen.css

---
## Architecture of the project
![Image description](https://github.com/vitalii7922/railway-reservation-system/blob/refactoring/src/main/java/com/tsystems/project/db/Architecture.jpg)

---
## Service level description
Service level consists of:
- Passenger service: add passengers to DB, get passengers data by id, get list of passengers are registered on a train
- Schedule service:   add a schedule on a train, get a schedule of a train, get list of schedules on a station
- Seats service: get a train between two stations by number
- Station service: add a station, get a station by id, get list of stations
- Ticket service: add a ticket to a train, get a ticket by passenger data
- Train service: add a train, get rout of a train by number, get list of trains between two points, get list of all trains, get list of a train between extreme stations, get a train object by an origin station, get a train object by a destination station.

---
## DAO layer description
DAO layer consists of: 
- Passenger DAO has methods: find passenger by personal data, find all passengers are registered on a train by train number;
-  Schedule DAO has methods: find schedule of a train by a train id, find list of schedules by station id;
- Station DAO has methods: find station by name;
- Ticket DAO has methods: find a ticket by a passenger data;
- Train DAO has methods: find a train by departure and arrival station name, find list of trains by a train number, find list of trains, by stations id at given term, find list of trains by train departure and arrival id.

---
## Junit tests description:
StationServiceTest: add a station
StationServiceTest: add a ticket, verify free seats, verify passenger data, and verify time departure
TrainServiceTest: add a train, get a train, get list of all trains, get list of all trains between two points. 

---
##Screenshots of the application
![Search trains](https://github.com/vitalii7922/railway-reservation-system/blob/refactoring/src/main/java/com/tsystems/project/db/searchTrains.jpg)

