package com.tsystems.project.dto;

import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Train;
import java.io.Serializable;
import java.util.List;

public class StationDto implements Serializable {

    private long id;

    private String name;

    private List<Train> trainsDeparture;

    private List<Train> trainsArrive;

    private List<Schedule> schedules;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Train> getTrainsDeparture() {
        return trainsDeparture;
    }

    public void setTrainsDeparture(List<Train> trainsDeparture) {
        this.trainsDeparture = trainsDeparture;
    }

    public List<Train> getTrainsArrive() {
        return trainsArrive;
    }

    public void setTrainsArrive(List<Train> trainsArrive) {
        this.trainsArrive = trainsArrive;
    }

}