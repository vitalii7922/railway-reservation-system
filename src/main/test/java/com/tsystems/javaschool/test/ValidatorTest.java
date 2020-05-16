package com.tsystems.javaschool.test;

import com.tsystems.project.converter.TicketConverter;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.dto.PassengerDto;
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
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ValidatorTest {

    @Mock
    TrainService trainServiceMock;

    @Mock
    TicketDao ticketDaoMock;

    @Mock
    PassengerService passengerServiceMock;

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
