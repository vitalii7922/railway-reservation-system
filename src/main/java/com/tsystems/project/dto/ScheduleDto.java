package com.tsystems.project.dto;
import java.io.Serializable;
import java.util.Objects;

public class ScheduleDto implements Serializable, Comparable<ScheduleDto> {
    long id;

    long trainId;

    int trainNumber;

    String stationName;

    private String arrivalTime;

    private String departureTime;

    public long getTrainId() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId = trainId;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleDto)) return false;
        ScheduleDto that = (ScheduleDto) o;
        return getTrainNumber() == that.getTrainNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainNumber());
    }

    @Override
    public int compareTo(ScheduleDto o) {
        return trainNumber - o.getTrainNumber();
    }
}
