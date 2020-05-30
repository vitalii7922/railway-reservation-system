/*
package com.tsystems.javaschool.test;

import static org.hamcrest.CoreMatchers.is;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.helper.TrainHelper;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TrainServiceTest {

    @Mock
    private TrainDao trainDaoMock;

    @Mock
    private TrainConverter trainConverterMock;

    @Mock
    private StationService stationServiceMock;

    @Spy
    private TimeConverter timeConverter;

    @Mock
    TrainHelper trainHelperMock;

    @Mock
    private ScheduleService scheduleServiceMock;

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
        when(stationServiceMock.getStationByName("Moscow")).thenReturn(station);
        assertThat(trainService.addTrain(trainDto), is(notNullValue()));
        resetMocks();
    }

    @Test
    public void testGetTrain() {
        //initialize schedule
        Schedule scheduleDeparture = new Schedule();
        scheduleDeparture.setDepartureTime(LocalDateTime.parse("2020-04-15T16:00"));
        Schedule scheduleArrival = new Schedule();
        scheduleArrival.setArrivalTime(LocalDateTime.parse("2020-04-16T16:00"));
        Station originStation = new Station("Moscow");
        Station destinationStation = new Station("Krasnodar");
        //initialize train
        Train train = new Train(1, 100, List.of(scheduleDeparture, scheduleArrival),
                originStation, destinationStation);
        //initialize train dto
        TrainDto trainDto = new TrainDto();
        trainDto.setNumber(1);
        trainDto.setSeats(100);
        trainDto.setArrivalTime("16-04-2020 16:00");
        trainDto.setDepartureTime("15-04-2020 16:00");
        trainDto.setOriginStation("Moscow");
        trainDto.setDestinationStation("Krasnodar");

        when(trainDaoMock.findByNumber(1)).thenReturn(train);
        when(trainConverterMock.convertToTrainDto(trainDaoMock.findByNumber(1))).thenReturn(trainDto);
        Assert.assertEquals(1, trainService.getTrainByNumber(1).getNumber());
        Assert.assertTrue(EqualsBuilder.reflectionEquals(trainDto, trainService.getTrainByNumber(1)));
    }

    @Test
    public void testGetTrainList() {
        //initialize schedule
        Schedule scheduleDeparture = new Schedule();
        scheduleDeparture.setDepartureTime(LocalDateTime.parse("2020-04-15T16:00"));
        Schedule scheduleArrival = new Schedule();
        scheduleArrival.setArrivalTime(LocalDateTime.parse("2020-04-16T16:00"));
        Station originStation = new Station("Moscow");
        Station destinationStation = new Station("Krasnodar");
        //initialize list of trains
        Train train1 = new Train(1, 100, List.of(scheduleDeparture, scheduleArrival),
                originStation, destinationStation);
        Train train2 = new Train(2, 50, List.of(scheduleDeparture, scheduleArrival),
                originStation, destinationStation);
        //initialize list of trainDto
        TrainDto trainDto1 = new TrainDto(1, 100, "15-04-2020 16:00",
                "16-04-2020 16:00", "Moscow", "Krasnodar");
        TrainDto trainDto2 = new TrainDto(2, 50, "15-04-2020 16:00",
                "16-04-2020 16:00", "Moscow", "Krasnodar");
        List<TrainDto> trainDtoListExpected = new ArrayList<>() {
            {
                add(trainDto1);
                add(trainDto2);
            }
        };

        when(trainDaoMock.findAll()).thenReturn(List.of(train1, train2));
        when(trainHelperMock.getTrainListBetweenExtremeStations(Arrays.asList(train1, train2)))
                .thenReturn(Arrays.asList(trainDto1, trainDto2));

        List<TrainDto> trainDtoListResult = trainService.getTrainList();
        for (int i = 0; i < trainDtoListResult.size(); i++) {
            Assert.assertTrue(EqualsBuilder.reflectionEquals(trainDtoListResult.get(i), trainDtoListExpected.get(i)));
        }
    }

    @Test
    public void testGetTrainListBetweenTwoPoints() {
        //initialize trainDto
        TrainDto trainDto = new TrainDto("Saint-Petersburg", "Moscow");
        trainDto.setDepartureTime("12-04-2020 12:00");
        trainDto.setArrivalTime("13-04-2020 01:00");
        //initialize schedule
        Schedule scheduleDeparture = new Schedule();
        scheduleDeparture.setDepartureTime(LocalDateTime.parse("2020-04-12T16:00"));
        Schedule scheduleArrival = new Schedule();
        scheduleArrival.setArrivalTime(LocalDateTime.parse("2020-04-13T00:30"));
        Station originStation = new Station("Saint-Petersburg");
        Station destinationStation = new Station("Moscow");
        //initialize train
        Train train = new Train(1, 100, List.of(scheduleDeparture, scheduleArrival),
                originStation, destinationStation);
        when(stationServiceMock.getStationByName("Saint-Petersburg")).thenReturn(originStation);
        when(stationServiceMock.getStationByName("Moscow")).thenReturn(destinationStation);
        when(trainDaoMock.findByStationsIdAtGivenTerm(stationServiceMock.getStationByName("Saint-Petersburg").getId(),
                stationServiceMock.getStationByName("Moscow").getId(),
                timeConverter.reversedConvertDateTime("12-04-2020 12:00"),
                timeConverter.reversedConvertDateTime("13-04-2020 01:00"))).thenReturn(List.of(train));
        //initialize trainDto
        TrainDto trainFounded = new TrainDto("Saint-Petersburg","Moscow");
        trainFounded.setNumber(1);
        trainFounded.setSeats(100);
        trainFounded.setDepartureTime("12-04-2020 16:00");
        trainFounded.setArrivalTime("13-04-2020 00:30");

        List<TrainDto> trainDtoListFounded = new ArrayList<>() {
            {
                add(trainFounded);
            }
        };
        when(trainHelperMock.searchTrainsBetweenTwoPoints(List.of(train))).thenReturn(trainDtoListFounded);

        for (TrainDto t : trainDtoListFounded) {
            String departureStation = t.getOriginStation();
            String arrivalStation = t.getDestinationStation();

            assertTrue(departureStation.equalsIgnoreCase(trainDto.getOriginStation()) &&
            arrivalStation.equalsIgnoreCase(trainDto.getDestinationStation()) &&
            timeConverter.reversedConvertDateTime(t.getDepartureTime())
                    .isAfter(timeConverter.reversedConvertDateTime(trainDto.getDepartureTime())) &&
                    timeConverter.reversedConvertDateTime(t.getArrivalTime())
                            .isBefore(timeConverter.reversedConvertDateTime(trainDto.getArrivalTime())));
        }
    }

    @After
    public void resetMocks() {
        Mockito.reset(trainDaoMock, stationServiceMock, scheduleServiceMock, trainConverterMock, timeConverter);
    }

}
*/
