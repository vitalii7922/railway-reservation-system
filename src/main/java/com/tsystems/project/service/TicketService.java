package com.tsystems.project.service;

import com.tsystems.project.converter.TicketConverter;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.TicketDto;
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
    TrainDao trainDao;

    @Autowired
    PassengerDao passengerDao;

    @Autowired
    TicketConverter ticketConverter;

    private static final Log log = LogFactory.getLog(PassengerService.class);

    @Transactional
    public TicketDto addTicket(int trainNumber, long stationFromId, long stationToId, PassengerDto passengerDto) {
        Train trainDeparture = trainDao.findByStationDepartureId(trainNumber, stationFromId);
        Train trainArrival = trainDao.findByStationArrivalId(trainNumber, stationToId);
        TicketDto ticketDto = null;
        try {
            List<Train> trains = trainDao.findAllTrainsBetweenTwoStations(trainDeparture.getId(), trainArrival.getId());
            Ticket t = new Ticket();
            t.setTrain(trainDeparture);
            Passenger passenger = passengerDao.findOne(passengerDto.getId());
            t.setPassenger(passenger);

            for (Train train : trains) {
                int seats = train.getSeats();
                seats--;
                train.setSeats(seats);
                trainDao.update(train);
            }

            t = ticketDao.create(t);
            ticketDto = ticketConverter.convertToTicketDto(t, passengerDto, trainDeparture, trainArrival);
        } catch (NullPointerException e) {
            log.error(e.getCause());
        }

        return ticketDto;
    }
}
