package com.tsystems.project.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * author Vitalii Nefedov
 */
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Station implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "originStation")
    private List<Train> trainsDeparture;

    @OneToMany(mappedBy = "destinationStation")
    private List<Train> trainsArrive;

    @OneToMany(mappedBy = "station")
    private List<Schedule> schedules;
}
