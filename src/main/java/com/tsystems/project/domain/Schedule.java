package com.tsystems.project.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * author Vitalii Nefedov
 */
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "arrive_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @ManyToOne
    @JoinColumn(name = "train_id")
    Train train;

    @ManyToOne
    @JoinColumn(name = "station_id")
    Station station;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return getId() == schedule.getId() &&
                Objects.equals(getArrivalTime(), schedule.getArrivalTime()) &&
                Objects.equals(getDepartureTime(), schedule.getDepartureTime()) &&
                Objects.equals(getTrain(), schedule.getTrain()) &&
                Objects.equals(getStation(), schedule.getStation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getArrivalTime(), getDepartureTime(), getTrain(), getStation());
    }
}
