package com.tsystems.javaschool.test;

import com.tsystems.project.converter.TicketConverter;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TicketServiceTest {

    @Mock
    TrainService trainServiceMock;

    @Mock
    TicketDao ticketDaoMock;

    @Mock
    PassengerService passengerServiceMock;

    @InjectMocks
    TicketService ticketService;

    @Mock
    TicketService ticketServiceMock;

    @InjectMocks
    TrainTicketValidator trainTicketValidator;


    @Spy
    TimeConverter timeConverter;

    @Mock
    TicketConverter ticketConverterMock;




    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTicket() {
        //initialize train
        Train train = new Train();
        train.setNumber(1);
        Station originStation1 = new Station();
        originStation1.setName("Moscow");
        Station destinationStation1 = new Station();
        destinationStation1.setName("Saint-Petersburg");
        Schedule schedule1 = new Schedule();
        schedule1.setDepartureTime(LocalDateTime.parse("2020-01-01T17:00"));
        train.setOriginStation(originStation1);
        train.setDestinationStation(destinationStation1);
        train.setSeats(10);
        train.setSchedules(List.of(schedule1));
        //initialize passengerTrainDto
        PassengerTrainDto passengerTrainDto = new PassengerTrainDto();
        passengerTrainDto.setTrainNumber(1);
        passengerTrainDto.setOriginStation("Moscow");
        passengerTrainDto.setDestinationStation("Krasnodar");
        //initialize passengerDto
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(1);
        Passenger passenger = new Passenger();
        passenger.setBirthDate(LocalDate.parse("2000-01-01"));
        passenger.setFirstName("Ivan");
        passenger.setSecondName("Ivanov");
        TrainDto trainDtoDeparture = new TrainDto();
        TrainDto trainDtoArrival = new TrainDto();
        when(trainServiceMock.getTrainByOriginStation(trainDtoDeparture)).thenReturn(train);
        when(trainServiceMock.getTrainByDestinationStation(trainDtoArrival)).thenReturn(train);
        when(trainServiceMock.getTrainListByTrainsId(train, train)).thenReturn(List.of(train));
        when(passengerServiceMock.getPassengerById(passengerDto.getId())).thenReturn(passenger);

        Ticket ticket = new Ticket();
        ticket.setTrain(train);
        ticket.setPassenger(passengerServiceMock.getPassengerById(passengerDto.getId()));
        ticket.setId(1);
        when(ticketDaoMock.create(ticket)).thenReturn(ticket);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setTrainNumber(1);
        ticketDto.setId(1);
        ticketDto.setFirstName("Ivan");
        ticketDto.setLastName("Ivanov");
        ticketDto.setBirthDate(LocalDate.parse("2000-01-01"));
        ticketDto.setStationOrigin("Moscow");
        ticketDto.setStationDeparture("Krasnodar");
        ticketDto.setDepartureTime("01-01-2020 17:00");
        ticketDto.setArrivalTime("03-01-2020 17:00");


        when(ticketConverterMock.convertToTicketDto(ticketDaoMock.create(ticket), passengerDto,
                trainServiceMock.getTrainByOriginStation(new TrainDto()),
                trainServiceMock.getTrainByDestinationStation(new TrainDto()))).thenReturn(ticketDto);

        TicketDto ticketDtoResult = ticketConverterMock.convertToTicketDto(ticketDaoMock.create(ticket), passengerDto,
                trainServiceMock.getTrainByOriginStation(new TrainDto()),
                trainServiceMock.getTrainByDestinationStation(new TrainDto()));

        assertEquals("Ivan", ticketService.addTicket(passengerTrainDto, passengerDto).getFirstName());
    }

    @Test
    public void TestVerifyFreeSeats() {
        TrainDto trainDto = new TrainDto();
        trainDto.setOriginStation("Moscow");
        trainDto.setDestinationStation("Saint-Petersburg");
        trainDto.setSeats(10);

        Train train = new Train();
        train.setSeats(10);
        when(trainServiceMock.getTrainByOriginStation(trainDto)).thenReturn(train);
        when(trainServiceMock.getTrainByDestinationStation(trainDto)).thenReturn(train);
        when(trainServiceMock.getTrainListByTrainsId(train, train)).thenReturn(List.of(train));
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
        Mockito.reset(ticketDaoMock, passengerServiceMock, trainServiceMock, timeConverter);
    }
}
