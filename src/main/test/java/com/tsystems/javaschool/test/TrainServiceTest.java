package com.tsystems.javaschool.test;

import static org.hamcrest.CoreMatchers.is;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.helper.TrainHelper;
import com.tsystems.project.model.Schedule;
import com.tsystems.project.model.Station;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.junit.Assert;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mockito.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TrainServiceTest {

    @Mock
    private TrainDao trainDaoMock;

    @Spy
    private TrainConverter trainConverter;

    @Mock
    private StationService stationService;

    @Spy
    private TrainHelper trainHelper;

    @Spy
    private TimeConverter timeConverter;

    @Mock
    private ScheduleService scheduleService;

    @InjectMocks
    private TrainService trainService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTrain() {
        when(trainDaoMock.create(any(Train.class))).thenReturn(new Train());
        TrainDto trainDto = new TrainDto(1, 100,
                "2020-04-15T16:00", "2020-04-16T17:00",
                "Moscow", "Krasnodar");
        Station station = new Station();
        station.setName("Moscow");
        when(stationService.getStationByName("Moscow")).thenReturn(station);
        assertThat(trainService.addTrain(trainDto), is(notNullValue()));
        resetMocks();
    }

    @Test
    public void testGetTrain() {
        Schedule scheduleDeparture = new Schedule();
        scheduleDeparture.setDepartureTime(LocalDateTime.parse("2020-04-15T16:00"));
        Schedule scheduleArrival = new Schedule();
        scheduleArrival.setArrivalTime(LocalDateTime.parse("2020-04-16T16:00"));
        Station originStation = new Station("Moscow");
        Station departureStation = new Station("Krasnodar");

        Train train = new Train(1, 100, List.of(scheduleDeparture, scheduleArrival),
                originStation, departureStation);

        when(trainDaoMock.findByNumber(1)).thenReturn(train);

        Assert.assertEquals(1, trainService.getTrainByNumber(1).getNumber());
        resetMocks();
    }

    @Test
    public void testGetAllTrains() {
        Schedule scheduleDeparture = new Schedule();
        scheduleDeparture.setDepartureTime(LocalDateTime.parse("2020-04-15T16:00"));
        Schedule scheduleArrival = new Schedule();
        scheduleArrival.setArrivalTime(LocalDateTime.parse("2020-04-16T16:00"));
        Station originStation = new Station("Moscow");
        Station departureStation = new Station("Krasnodar");

        Train train1 = new Train(1, 100, List.of(scheduleDeparture, scheduleArrival),
                originStation, departureStation);
        Train train2 = new Train(2, 50, List.of(scheduleDeparture, scheduleArrival),
                originStation, departureStation);
        when(trainDaoMock.findAll()).thenReturn(List.of(train1, train2));

        Assert.assertEquals(2, trainService.getAllTrains().size());
        resetMocks();
    }



    public void resetMocks() {
        Mockito.reset(trainDaoMock, stationService, scheduleService, trainConverter, timeConverter);
    }

}
