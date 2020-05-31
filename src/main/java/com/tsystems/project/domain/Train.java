package com.tsystems.project.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * author Vitalii Nefedov
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
