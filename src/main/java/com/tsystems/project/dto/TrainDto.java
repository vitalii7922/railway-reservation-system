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
        return getId() == trainDto.getId() &&
                getNumber() == trainDto.getNumber() &&
                getSeats() == trainDto.getSeats() &&
                Objects.equals(getArrivalTime(), trainDto.getArrivalTime()) &&
                Objects.equals(getDepartureTime(), trainDto.getDepartureTime()) &&
                Objects.equals(getOriginStation(), trainDto.getOriginStation()) &&
                Objects.equals(getDestinationStation(), trainDto.getDestinationStation()) &&
                Objects.equals(getAllTrainsArrivalTime(), trainDto.getAllTrainsArrivalTime()) &&
                Objects.equals(getAllTrainsDepartureTime(), trainDto.getAllTrainsDepartureTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getSeats(), getArrivalTime(), getDepartureTime(), getOriginStation(),
                getDestinationStation(), getAllTrainsArrivalTime(), getAllTrainsDepartureTime());
    }

    @Override
    public int compareTo(TrainDto o) {
        return number - o.number;
    }
}
