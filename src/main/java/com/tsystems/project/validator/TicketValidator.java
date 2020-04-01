package com.tsystems.project.validator;

import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.PassengerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
public class TicketValidator {

    @Autowired
    TrainDao trainDao;

    @Autowired
    TicketDao ticketDao;

    public boolean verifyTime(String departureTime) throws DateTimeException {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), LocalDateTime.parse(departureTime));
        return minutes >= 10;
    }

    public boolean verifySeats(int trainNumber, long stationFromId, long stationToId) throws NullPointerException {
        Train trainDeparture = trainDao.findByStationDepartureId(trainNumber, stationFromId);
        Train trainArrival = trainDao.findByStationArrivalId(trainNumber, stationToId);
        List<Train> trains = trainDao.findAllTrainsBetweenTwoStations(trainDeparture.getId(), trainArrival.getId());
        return trains.stream().allMatch(t -> t.getSeats() > 0);
    }

    public boolean verifyPassenger(int trainNumber, PassengerDto passengerDto) {
        Ticket ticket = ticketDao.findByPassenger(trainNumber, passengerDto);
        return ticket == null;
    }

    @Bean
    public TicketValidator transferService() {
        return new TicketValidator();
    }
}
