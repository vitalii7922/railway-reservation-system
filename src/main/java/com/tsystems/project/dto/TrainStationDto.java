package com.tsystems.project.dto;

import com.tsystems.project.domain.Station;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TrainStationDto implements Serializable {
    private Station station;
//    private LocalDateTime departureTime;
//    private LocalDateTime arrivalTime;
    private String departureTime;
    private String arrivalTime;



    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    /*public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }*/

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
