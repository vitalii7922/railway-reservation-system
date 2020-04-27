package com.tsystems.project.service;

import com.tsystems.project.converter.TicketConverter;
import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.model.Passenger;
import com.tsystems.project.model.Ticket;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TicketDto;
import com.tsystems.project.dto.TrainDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    TrainService trainService;

    @Autowired
    TicketConverter ticketConverter;

    private static final Log log = LogFactory.getLog(TicketService.class);


    @Transactional
    public TicketDto addTicket(PassengerTrainDto passengerTrainDto, PassengerDto passengerDto) {
        TrainDto trainDto = new TrainDto();
        trainDto.setNumber(passengerTrainDto.getTrainNumber());
        trainDto.setOriginStation(passengerTrainDto.getOriginStation());
        trainDto.setDestinationStation(passengerTrainDto.getDestinationStation());
        Train trainDeparture = trainService.getTrainByOriginStation(trainDto);
        Train trainArrival = trainService.getTrainByDestinationStation(trainDto);
        TicketDto ticketDto;

        List<Train> trains = trainService.getTrainsBetweenTwoStations(trainDeparture, trainArrival);
        Passenger passenger = passengerService.getPassengerById(passengerDto.getId());
        Ticket t = null;
        for (Train train : trains) {
            t = new Ticket();
            t.setTrain(train);
            t.setPassenger(passenger);
            int seats = train.getSeats();
            seats--;
            train.setSeats(seats);
            trainService.updateTrain(train);
            t = ticketDao.create(t);
        }
        ticketDto = ticketConverter.convertToTicketDto(t, passengerDto, trainDeparture, trainArrival);
        log.info("---------ticket has been added--------------");
        return ticketDto;
    }

    public Ticket getTicketByPassenger(TrainDto trainDto, PassengerDto passengerDto) {
        return ticketDao.findByPassenger(trainDto, passengerDto);
    }
}
