package com.tsystems.project.dto;

import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;

import java.io.Serializable;
import java.util.List;

public class TrainDto implements Serializable {

    private long id;

    private int number;

    int seats;

    private List<Schedule> schedules;

    private Station originStation;

    private Station destinationStation;


    public long getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
    }

    public Station getOriginStation() {
        return originStation;
    }

    public void setOriginStation(Station originStation) {
        this.originStation = originStation;
    }

}
