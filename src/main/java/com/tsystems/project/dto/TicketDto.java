package com.tsystems.project.dto;

import java.time.LocalDate;

public class TicketDto {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String stationOrigin;
    private String stationDeparture;
    private String departureTime;
    private String arrivalTime;
    private int trainNumber;

    public long getId() {
        return id;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getStationOrigin() {
        return stationOrigin;
    }

    public void setStationOrigin(String stationOrigin) {
        this.stationOrigin = stationOrigin;
    }

    public String getStationDeparture() {
        return stationDeparture;
    }

    public void setStationDeparture(String stationDeparture) {
        this.stationDeparture = stationDeparture;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }
}
