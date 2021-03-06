package com.tsystems.project.service;
import com.tsystems.project.converter.TicketMapper;
import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TicketDto;
import com.tsystems.project.dto.TrainDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

/**
 * author Vitalii Nefedov
 */

@Service
public class TicketService {
    private final TicketDao ticketDao;

    private final PassengerService passengerService;

    private final TrainService trainService;

    private final TicketMapper ticketConverter;

    private static final Log log = LogFactory.getLog(TicketService.class);

    public TicketService(TicketDao ticketDao, PassengerService passengerService, TrainService trainService,
                         TicketMapper ticketConverter) {
        this.ticketDao = ticketDao;
        this.passengerService = passengerService;
        this.trainService = trainService;
        this.ticketConverter = ticketConverter;
    }

    /**
     * decrease number of seats, add passenger to a train, add a ticket, update train data
     *
     * @param passengerTrainDto contains train number, origin station name, destination station name
     * @param passengerDto      contains firstName, secondName, birthDate
     * @return ticketDto
     */
    @Transactional
    public TicketDto addTicket(PassengerTrainDto passengerTrainDto, PassengerDto passengerDto) {
        TrainDto trainDto = TrainDto.builder()
                .originStation(passengerTrainDto.getOriginStation())
                .destinationStation(passengerTrainDto.getDestinationStation())
                .number(passengerTrainDto.getTrainNumber())
                .build();
        Train trainDeparture = trainService.getTrainByOriginStation(trainDto);
        Train trainArrival = trainService.getTrainByDestinationStation(trainDto);
        TicketDto ticketDto;
        List<Train> trains = trainService.getTrainListByTrainsId(trainDeparture, trainArrival);
        Passenger passenger = passengerService.getPassengerById(passengerDto.getId());
        Ticket ticket = null;
        for (Train train : trains) {
            ticket = Ticket.builder()
                    .train(train)
                    .passenger(passenger)
                    .build();
            ticket = ticketDao.create(ticket);
            int seats = train.getSeats();
            seats--;
            train.setSeats(seats);
            trainService.updateTrain(train);
        }
        ticketDto = ticketConverter.convertToTicketDto(ticket, passengerDto, trainDeparture, trainArrival);
        log.info("---------ticket has been added--------Passenger data: " +
                passenger.getFirstName() + " " + passenger.getSecondName() + " " + passenger.getBirthDate());
        return ticketDto;
    }


    /**
     * @param trainDto     contains train id
     * @param passengerDto contains passenger id
     * @return ticket
     */
    public Ticket getTicketByPassenger(TrainDto trainDto, PassengerDto passengerDto) {
        return ticketDao.findByPassenger(trainDto, passengerDto);
    }
}
