package com.tsystems.project.validator;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public abstract class Verification {

    @Autowired
    TimeConverter timeConverter;

    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;

    @Autowired
    TicketService ticketService;

    public boolean verifyTime(TrainDto trainDto) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(),
                LocalDateTime.parse(timeConverter.reversedConvertDateTime(trainDto.getDepartureTime()).toString()));
        return minutes <= 10;
    }

    public boolean verifyTime(PassengerTrainDto passengerTrainDto) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(),
                LocalDateTime.parse(timeConverter.reversedConvertDateTime(passengerTrainDto.getDepartureTime()).toString()));
        return minutes <= 10;
    }

    public boolean verifySeats(TrainDto trainDto) {
        Train trainDeparture = trainService.getTrainByOriginStation(trainDto);
        Train trainArrival = trainService.getTrainByDestinationStation(trainDto);
        List<TrainDto> trains = trainService.getTrainsDtoBetweenTwoStations(trainDeparture, trainArrival);
        return !trains.stream().allMatch(t -> t.getSeats() > 0);
    }

    public boolean verifySeats(PassengerTrainDto passengerTrainDto) {
        TrainDto trainDto = new TrainDto();
        trainDto.setNumber(passengerTrainDto.getTrainNumber());
        trainDto.setOriginStation(passengerTrainDto.getOriginStation());
        trainDto.setDestinationStation(passengerTrainDto.getDestinationStation());
        Train trainDeparture = trainService.getTrainByOriginStation(trainDto);
        Train trainArrival = trainService.getTrainByDestinationStation(trainDto);
        List<TrainDto> trains = trainService.getTrainsDtoBetweenTwoStations(trainDeparture, trainArrival);
        return !trains.stream().allMatch(t -> t.getSeats() > 0);
    }

    public boolean verifyPassenger(int trainNumber, PassengerDto passengerDto) {
        Ticket ticket = ticketService.getTicketByPassenger(trainNumber, passengerDto);
        return ticket != null;
    }
}
