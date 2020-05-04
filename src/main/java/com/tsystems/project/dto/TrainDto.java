package com.tsystems.project.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class TrainDto implements Serializable, Comparable<TrainDto> {

    private long id;

    private int number;

    int seats;

    String arrivalTime;

    String departureTime;

    @NotNull(message = "Origin station must not be empty")
    @NotEmpty(message = "Origin station must not be empty")
    private String originStation;

    @NotNull(message = "Destination station must not be empty")
    @NotEmpty(message = "Destination station must not be empty")
    private String destinationStation;

    String allTrainsArrivalTime;

    String allTrainsDepartureTime;

    public TrainDto() {
    }

    public TrainDto(int number, int seats, String departureTime, String arrivalTime, String originStation,
                    String destinationStation) {
        this.number = number;
        this.seats = seats;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.originStation = originStation;
        this.destinationStation = destinationStation;
    }

    public String getAllTrainsArrivalTime() {
        return allTrainsArrivalTime;
    }

    public void setAllTrainsArrivalTime(String allTrainsArrivalTime) {
        this.allTrainsArrivalTime = allTrainsArrivalTime;
    }

    public String getAllTrainsDepartureTime() {
        return allTrainsDepartureTime;
    }

    public void setAllTrainsDepartureTime(String allTrainsDepartureTime) {
        this.allTrainsDepartureTime = allTrainsDepartureTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainDto)) return false;
        TrainDto trainDto = (TrainDto) o;
        return getNumber() == trainDto.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }

    @Override
    public int compareTo(TrainDto o) {
        return number - o.number;
    }
}
