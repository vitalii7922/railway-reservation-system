package com.tsystems.project.service;

import com.tsystems.project.converter.TicketConverter;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TicketDto;
import com.tsystems.project.dto.TrainDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketDao ticketDao;

    @Autowired
    PassengerService passengerService;

    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;

    @Autowired
    TicketConverter ticketConverter;

    @Autowired
    TrainDao trainDao;


    @Transactional
    public TicketDto addTicket(PassengerTrainDto passengerTrainDto, PassengerDto passengerDto) {
        TrainDto trainDto = new TrainDto();
        trainDto.setNumber(passengerTrainDto.getTrainNumber());
        trainDto.setOriginStation(passengerTrainDto.getOriginStation());
        trainDto.setDestinationStation(passengerTrainDto.getDestinationStation());
        Train trainDeparture = trainService.getTrainByOriginStation(trainDto);
        Train trainArrival = trainService.getTrainByDestinationStation(trainDto);
        TicketDto ticketDto = null;

        List<Train> trains = trainService.getTrainsBetweenTwoStations(trainDeparture, trainArrival);
        Ticket t = new Ticket();
        t.setTrain(trainDeparture);
        Passenger passenger = passengerService.getPassengerById(passengerDto.getId());
        t.setPassenger(passenger);
        for (Train train : trains) {
            int seats = train.getSeats();
            seats--;
            train.setSeats(seats);
            trainService.updateTrain(train);
        }
        t = ticketDao.create(t);
        ticketDto = ticketConverter.convertToTicketDto(t, passengerDto, trainDeparture, trainArrival);

        return ticketDto;
    }

    public Ticket getTicketByPassenger(int trainNumber, PassengerDto passengerDto) {
        return ticketDao.findByPassenger(trainNumber, passengerDto);
    }
}
