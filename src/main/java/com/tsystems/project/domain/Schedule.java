package com.tsystems.project.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
}
