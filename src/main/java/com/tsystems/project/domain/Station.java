package com.tsystems.project.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Station implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "station_name")
    private String name;

    @OneToMany(mappedBy = "originStation")
    private List<Train> trainsDeparture;

    @OneToMany(mappedBy = "destinationStation")
    private List<Train> trainsArrive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
