package com.tsystems.project.dto;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ScheduleDto implements Serializable {

    long id;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    Train train;

    Station station;

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

