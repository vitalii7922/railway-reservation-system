package com.tsystems.project.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * author Vitalii Nefedov
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainStationDto implements Serializable {

    private String station;

    private String departureTime;

    private String arrivalTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainStationDto)) return false;
        TrainStationDto that = (TrainStationDto) o;
        return Objects.equals(getStation(), that.getStation()) &&
                Objects.equals(getDepartureTime(), that.getDepartureTime()) &&
                Objects.equals(getArrivalTime(), that.getArrivalTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStation(), getDepartureTime(), getArrivalTime());
    }
}
