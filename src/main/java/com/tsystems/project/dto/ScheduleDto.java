package com.tsystems.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * author Vitalii Nefedov
 */
@Builder
@Getter
@Setter
public class ScheduleDto implements Serializable, Comparable<ScheduleDto> {
    long id;

    long trainId;

    int trainNumber;

    String stationName;

    private String arrivalTime;

    private String departureTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleDto)) return false;
        ScheduleDto that = (ScheduleDto) o;
        return getId() == that.getId() &&
                getTrainId() == that.getTrainId() &&
                getTrainNumber() == that.getTrainNumber() &&
                getStationName().equals(that.getStationName()) &&
                Objects.equals(getArrivalTime(), that.getArrivalTime()) &&
                Objects.equals(getDepartureTime(), that.getDepartureTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTrainId(), getTrainNumber(), getStationName(), getArrivalTime(), getDepartureTime());
    }

    @Override
    public int compareTo(ScheduleDto o) {
        return trainNumber - o.getTrainNumber();
    }
}
