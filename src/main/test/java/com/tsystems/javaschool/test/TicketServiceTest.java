package com.tsystems.javaschool.test;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TicketDto;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.model.*;
import com.tsystems.project.service.PassengerService;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.validator.TrainTicketValidator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TicketServiceTest {

    @Mock
    TrainService trainService;

    @Mock
    TicketDao ticketDao;

    @Mock
    PassengerService passengerService;

    @InjectMocks
    TicketService ticketService;

    @Mock
    TicketService ticketServiceMock;

    @InjectMocks
    TrainTicketValidator trainTicketValidator;

    @Spy
    TimeConverter timeConverter;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTicket() {
        Train train1 = new Train();
        train1.setNumber(1);
        Station originStation1 = new Station();
        originStation1.setName("Moscow");
        Station destinationStation1 = new Station();
        destinationStation1.setName("Saint-Petersburg");
        Schedule schedule1 = new Schedule();
        schedule1.setDepartureTime(LocalDateTime.parse("2020-01-01T17:00"));
        train1.setOriginStation(originStation1);
        train1.setDestinationStation(destinationStation1);
        train1.setSeats(10);
        train1.setSchedules(List.of(schedule1));

        Train train2 = new Train();
        train2.setNumber(1);
        Station originsStation2 = new Station();
        originsStation2.setName("Saint-Petersburg");
        Station destinationStation2 = new Station();
        Schedule schedule2 = new Schedule();
        schedule2.setArrivalTime(LocalDateTime.parse("2020-01-03T17:00"));
        destinationStation2.setName("Krasnodar");
        train2.setOriginStation(originsStation2);
        train2.setDestinationStation(destinationStation2);
        train2.setSeats(10);

        PassengerTrainDto passengerTrainDto = new PassengerTrainDto();
        passengerTrainDto.setTrainNumber(1);
        passengerTrainDto.setOriginStation("Moscow");
        passengerTrainDto.setDestinationStation("Krasnodar");

        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(1);
        Passenger passenger = new Passenger();
        passenger.setBirthDate(LocalDate.parse("2000-01-01"));
        passenger.setFirstName("Ivan");
        passenger.setSecondName("Ivanov");

        List<Train> trainList = new ArrayList<>() {
            {
                add(train1);
                add(train2);
            }
        };


        when(trainService.getTrainByOriginStation(new TrainDto())).thenReturn(train1);
        when(trainService.getTrainByDestinationStation(new TrainDto())).thenReturn(train2);
        when(trainService.getTrainListByTrainsId(train1, train2)).thenReturn(trainList);
        when(passengerService.getPassengerById(passengerDto.getId())).thenReturn(passenger);
        Ticket ticket = new Ticket();
        ticket.setTrain(train1);
        ticket.setPassenger(passenger);
        ticket.setId(1);
        when(ticketDao.create(ticket)).thenReturn(ticket);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setTrainNumber(1);
        ticketDto.setId(1);
        ticketDto.setFirstName("Ivan");
        ticketDto.setFirstName("Ivanov");
        ticketDto.setBirthDate(LocalDate.parse("2000-01-01"));
        ticketDto.setStationOrigin("Moscow");
        ticketDto.setStationDeparture("Krasnodar");
        ticketDto.setDepartureTime("01-01-2020 17:00");
        ticketDto.setArrivalTime("03-01-2020 17:00");
        assertTrue(EqualsBuilder.reflectionEquals(ticketDto, ticketService.addTicket(passengerTrainDto, passengerDto)));
    }

    @Test
    public void TestVerifyFreeSeats() {
        TrainDto trainDto = new TrainDto();
        trainDto.setOriginStation("Moscow");
        trainDto.setDestinationStation("Saint-Petersburg");
        trainDto.setSeats(10);

        Train train = new Train();
        train.setSeats(10);
        when(trainService.getTrainByOriginStation(trainDto)).thenReturn(train);
        when(trainService.getTrainByDestinationStation(trainDto)).thenReturn(train);
        when(trainService.getTrainListByTrainsId(train, train)).thenReturn(List.of(train));
        assertFalse(trainTicketValidator.verifyFreeSeats(trainDto));
    }

    @Test
    public void TestVerifyPassenger() {
        Ticket ticket = new Ticket();
        TrainDto trainDto = new TrainDto();
        PassengerDto passengerDto = new PassengerDto();
        when(ticketServiceMock.getTicketByPassenger(trainDto, passengerDto)).thenReturn(ticket);
        assertTrue(trainTicketValidator.verifyPassenger(List.of(trainDto), passengerDto));
    }

    @Test
    public void TestVerifyTimeDeparture() {
        TrainDto trainDto = new TrainDto();
        trainDto.setDepartureTime("08-05-2020 12:00");
        assertFalse(trainTicketValidator.verifyTimeDeparture(trainDto));
    }

    @After
    public void resetMocks() {
        Mockito.reset(ticketDao, passengerService, trainService);
    }
}
