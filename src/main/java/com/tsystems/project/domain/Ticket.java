package com.tsystems.project.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Train train;

    @ManyToOne
    private Passenger passenger;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", train=" + train +
                ", passenger=" + passenger +
                '}';
    }
}
