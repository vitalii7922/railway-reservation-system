package com.tsystems.project.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * author Vitalii Nefedov
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Train train;

    @ManyToOne
    private Passenger passenger;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return getId() == ticket.getId() &&
                Objects.equals(getTrain(), ticket.getTrain()) &&
                Objects.equals(getPassenger(), ticket.getPassenger());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTrain(), getPassenger());
    }
}
