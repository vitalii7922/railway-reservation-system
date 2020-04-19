package com.tsystems.project.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class PassengerTrainDto implements Serializable {

    private int trainNumber;

    private int seats;

    private String originStation;

    private String destinationStation;

    private String departureTime;

    @NotEmpty(message = "Incorrect first name")
    private String firstName;

    @NotEmpty(message = "Incorrect second name")
    private String secondName;

    private String birthDate;

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getOriginStation() {
        return originStation;
    }

    public void setOriginStation(String originStation) {
        this.originStation = originStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
