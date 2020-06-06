package com.tsystems.project.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * author Vitalii Nefedov
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String stationOrigin;

    private String stationDeparture;

    private String departureTime;

    private String arrivalTime;

    private int trainNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketDto)) return false;
        TicketDto ticketDto = (TicketDto) o;
        return getId() == ticketDto.getId() &&
                getTrainNumber() == ticketDto.getTrainNumber() &&
                Objects.equals(getFirstName(), ticketDto.getFirstName()) &&
                Objects.equals(getLastName(), ticketDto.getLastName()) &&
                Objects.equals(getBirthDate(), ticketDto.getBirthDate()) &&
                Objects.equals(getStationOrigin(), ticketDto.getStationOrigin()) &&
                Objects.equals(getStationDeparture(), ticketDto.getStationDeparture()) &&
                Objects.equals(getDepartureTime(), ticketDto.getDepartureTime()) &&
                Objects.equals(getArrivalTime(), ticketDto.getArrivalTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getBirthDate(), getStationOrigin(),
                getStationDeparture(), getDepartureTime(), getArrivalTime(), getTrainNumber());
    }
}
