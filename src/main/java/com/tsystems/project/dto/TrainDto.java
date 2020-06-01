package com.tsystems.project.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * author Vitalii Nefedov
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainDto implements Serializable, Comparable<TrainDto> {

    private long id;

    private int number;

    int seats;

    private String arrivalTime;

    private String departureTime;

    private String originStation;

    private String destinationStation;

    private String allTrainsArrivalTime;

    private String allTrainsDepartureTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainDto)) return false;
        TrainDto trainDto = (TrainDto) o;
        return getNumber() == trainDto.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }

    @Override
    public int compareTo(TrainDto o) {
        return number - o.number;
    }
}
