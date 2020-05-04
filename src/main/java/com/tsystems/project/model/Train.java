package com.tsystems.project.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Train implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private int number;

    int seats;

    @OneToMany(mappedBy = "train")
    private List<Schedule> schedules;

    @ManyToOne
    @JoinColumn(name = "originStation_id")
    private Station originStation;

    @ManyToOne
    @JoinColumn(name = "destinationStation_id")
    private Station destinationStation;

    @OneToMany(mappedBy = "train")
    private List<Ticket> tickets;

    public Train() {
    }

    public Train(int number, int seats, List<Schedule> schedules, Station originStation, Station destinationStation) {
        this.number = number;
        this.seats = seats;
        this.schedules = schedules;
        this.originStation = originStation;
        this.destinationStation = destinationStation;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setOriginStation(Station originStation) {
        this.originStation = originStation;
    }
}
