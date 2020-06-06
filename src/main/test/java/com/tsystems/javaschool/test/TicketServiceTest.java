package com.tsystems.javaschool.test;
import com.tsystems.javaschool.test.config.TestConfig;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.*;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TicketDto;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class TicketServiceTest {

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    TrainDao trainDao;

    @Autowired
    PassengerDao passengerDao;

    private static Station stationMoscow;

    private static Station stationPetersburg;

    private static Station stationMurmansk;

    private static Train train1Id1;

    private static Train train1Id2;

    private static Ticket ticketFromMoscowToPetersburg;

    private static Ticket ticketFromPetersburgToMurmansk;

    private static Passenger passenger;

    private static void setUpTicket() {
        ticketFromMoscowToPetersburg = Ticket.builder()
                .train(train1Id1)
                .passenger(passenger)
                .build();

        ticketFromPetersburgToMurmansk = Ticket.builder()
                .train(train1Id2)
                .passenger(passenger)
                .build();
    }

    private static void setUpPassenger() {
        passenger = Passenger.builder()
                .id(1)
                .firstName("Ivan")
                .secondName("Ivanov")
                .birthDate(LocalDate.parse("2000-01-01"))
                .build();
    }

    private static void setUpTrain() {
        Schedule scheduleDepartureTrain1Id1 = Schedule.builder()
                .id(1)
                .departureTime(LocalDateTime.parse("2020-05-30T10:00"))
                .station(stationMoscow)
                .build();

        Schedule scheduleArrivalTrain1Id1 = Schedule.builder()
                .id(2)
                .arrivalTime(LocalDateTime.parse("2020-05-30T15:00"))
                .station(stationPetersburg)
                .build();

        train1Id1 = Train.builder()
                .id(1)
                .number(1)
                .originStation(stationMoscow)
                .destinationStation(stationPetersburg)
                .seats(100)
                .schedules(Arrays.asList(scheduleDepartureTrain1Id1, scheduleArrivalTrain1Id1))
                .build();

        Schedule scheduleDepartureTrain1Id2 = Schedule.builder()
                .id(1)
                .departureTime(LocalDateTime.parse("2020-05-30T15:10"))
                .station(stationPetersburg)
                .build();

        Schedule scheduleArrivalTrain1Id2 = Schedule.builder()
                .id(2)
                .arrivalTime(LocalDateTime.parse("2020-05-30T22:00"))
                .station(stationMurmansk)
                .build();

        train1Id2 = Train.builder()
                .id(2)
                .number(1)
                .originStation(stationPetersburg)
                .destinationStation(stationMurmansk)
                .seats(100)
                .schedules(Arrays.asList(scheduleDepartureTrain1Id2, scheduleArrivalTrain1Id2))
                .build();
    }

    private static void setUpStation() {
        stationMoscow = Station.builder()
                .id(1)
                .name("Moscow")
                .build();

        stationPetersburg = Station.builder()
                .id(2)
                .name("Saint-Petersburg")
                .build();

        stationMurmansk = Station.builder()
                .id(3)
                .name("Murmansk")
                .build();
    }

    @BeforeClass
    public static void setUp() {
        setUpStation();
        setUpTrain();
        setUpPassenger();
        setUpTicket();
    }

    @Test
    public void testAddTicket() {
        PassengerDto passengerDto = PassengerDto.builder()
                .id(1)
                .firstName("Ivan")
                .secondName("Ivanov")
                .birthDate(LocalDate.parse("2000-01-01"))
                .build();

        PassengerTrainDto passengerTrainDto = PassengerTrainDto.builder()
                .trainNumber(1)
                .departureTime("2020-30-05 10:00")
                .originStation("Moscow")
                .destinationStation("Saint-Petersburg")
                .build();

        Ticket ticket1 = Ticket.builder()
                .id(1)
                .train(train1Id1)
                .passenger(passenger)
                .build();

        Ticket ticket2 = Ticket.builder()
                .id(2)
                .train(train1Id2)
                .passenger(passenger)
                .build();

        Mockito.when(trainDao.findByOriginStation(passengerTrainDto.getTrainNumber(),
                passengerTrainDto.getOriginStation())).thenReturn(train1Id1);
        Mockito.when(trainDao.findByDestinationStation(passengerTrainDto.getTrainNumber(),
                passengerTrainDto.getDestinationStation())).thenReturn(train1Id2);
        Mockito.when(trainDao.findTrainListByTrainDepartureAndArrivalId(train1Id1.getNumber(), train1Id1.getId(),
                train1Id2.getId())).thenReturn(Arrays.asList(train1Id1, train1Id2));
        Mockito.when(passengerDao.findOne(1)).thenReturn(passenger);
        Mockito.doNothing().when(Mockito.mock(TrainService.class)).updateTrain(any());
        Mockito.when(ticketDao.create(ticketFromMoscowToPetersburg)).thenReturn(ticket1);
        Mockito.when(ticketDao.create(ticketFromPetersburgToMurmansk)).thenReturn(ticket2);

        TicketDto ticketDto = TicketDto.builder()
                .id(2)
                .trainNumber(1)
                .departureTime("30-05-2020 10:00")
                .arrivalTime("30-05-2020 22:00")
                .stationOrigin("Moscow")
                .stationDeparture("Murmansk")
                .firstName("Ivan")
                .lastName("Ivanov")
                .birthDate(LocalDate.parse("2000-01-01"))
                .build();

        Assert.assertEquals(ticketService.addTicket(passengerTrainDto, passengerDto), ticketDto);
    }
}
