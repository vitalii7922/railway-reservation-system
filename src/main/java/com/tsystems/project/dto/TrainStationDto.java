package com.tsystems.project.dto;

import java.io.Serializable;

/**
 * author Vitalii Nefedov
 */
public class TrainStationDto implements Serializable {
    private String station;
    private String departureTime;
    private String arrivalTime;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

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
