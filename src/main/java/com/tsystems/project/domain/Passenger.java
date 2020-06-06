package com.tsystems.project.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
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
public class Passenger implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String secondName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "passenger")
    private List<Ticket> tickets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;
        Passenger passenger = (Passenger) o;
        return getFirstName().equals(passenger.getFirstName()) &&
                getSecondName().equals(passenger.getSecondName()) &&
                getBirthDate().equals(passenger.getBirthDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getSecondName(), getBirthDate());
    }
}
