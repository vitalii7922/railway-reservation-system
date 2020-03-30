package com.tsystems.project.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Station implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "originStation", fetch = FetchType.EAGER)
    private List<Train> trainsDeparture;

    @OneToMany(mappedBy = "destinationStation")
    private List<Train> trainsArrive;

    @OneToMany(mappedBy = "station")
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

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id == station.id &&
                name.equals(station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
