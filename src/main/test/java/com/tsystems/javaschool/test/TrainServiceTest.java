package com.tsystems.javaschool.test;

import com.tsystems.javaschool.test.config.TestConfig;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class TrainServiceTest {

    @Autowired
    private TrainDao trainDao;

    @Autowired
    private TrainService trainService;

    @Autowired
    StationService stationService;

    @Autowired
    ScheduleDao scheduleDao;

    private static Train train1Id1;

    private static Train train1Id2;

    private static Train train2Id3;

    private static Station stationMoscow;

    private static Station stationPetersburg;

    private static Station stationMurmansk;

    private static Schedule scheduleDepartureTrain1Id1;

    private static Schedule scheduleArrivalTrain1Id1;

    private static Schedule scheduleDepartureTrain1Id2;

    private static Schedule scheduleArrivalTrain1Id2;

    private static Station setUpStation(String name, long id) {
        return Station.builder()
                .id(id)
                .name(name)
                .build();
    }

    private static void setUpTrain() {
        train1Id1 = Train.builder()
                .id(1L)
                .number(1)
                .originStation(stationMoscow)
                .destinationStation(stationPetersburg)
                .seats(100)
                .schedules(Arrays.asList(scheduleDepartureTrain1Id1, scheduleArrivalTrain1Id1))
                .build();
        train1Id1.setSchedules(Arrays.asList(scheduleDepartureTrain1Id1, scheduleArrivalTrain1Id1));

        train1Id2 = Train.builder()
                .id(2L)
                .number(1)
                .originStation(stationPetersburg)
                .destinationStation(stationMurmansk)
                .seats(100)
                .schedules(Arrays.asList(scheduleDepartureTrain1Id2, scheduleArrivalTrain1Id2))
                .build();

        train2Id3 = Train.builder()
                .id(3L)
                .number(2)
                .originStation(stationMoscow)
                .destinationStation(stationPetersburg)
                .seats(100)
                .schedules(Arrays.asList(scheduleDepartureTrain1Id1, scheduleArrivalTrain1Id1))
                .build();
    }

    private static Schedule setUpScheduleArrival(long id, LocalDateTime time, Station station) {
        return Schedule.builder()
                .id(id)
                .arrivalTime(time)
                .station(station)
                .build();
    }

    private static Schedule setUpScheduleDeparture(long id, LocalDateTime time, Station station) {
        return Schedule.builder()
                .id(id)
                .departureTime(time)
                .station(station)
                .build();
    }

    @BeforeClass
    public static void setUp() {
        stationMoscow = setUpStation("Moscow", 1);
        stationPetersburg = setUpStation("Saint-Petersburg", 2);
        stationMurmansk = setUpStation("Murmansk", 3);
        scheduleDepartureTrain1Id1 = setUpScheduleDeparture(2, LocalDateTime.parse("2020-05-30T15:10"), stationMoscow);
        scheduleArrivalTrain1Id1 = setUpScheduleArrival(1, LocalDateTime.parse("2020-05-30T20:00"), stationPetersburg);
        scheduleDepartureTrain1Id2 = setUpScheduleDeparture(3, LocalDateTime.parse("2020-05-30T20:10"), stationPetersburg);
        scheduleArrivalTrain1Id2 = setUpScheduleArrival(4, LocalDateTime.parse("2020-05-30T23:10"), stationMurmansk);
        setUpTrain();
    }

    @Test
    public void testAddTrain() {
        //parameter in add train
        TrainDto trainDto = TrainDto.builder()
                .id(1L)
                .number(1)
                .originStation("Moscow")
                .destinationStation("Saint-Petersburg")
                .seats(100)
                .departureTime("2020-05-30T15:10")
                .arrivalTime("2020-05-30T20:00")
                .build();

        Mockito.when(trainDao.create(any())).thenReturn(train1Id1);
        when(scheduleDao.create(scheduleDepartureTrain1Id1)).thenReturn(scheduleDepartureTrain1Id1);
        when(scheduleDao.create(scheduleArrivalTrain1Id1)).thenReturn(scheduleArrivalTrain1Id1);
        Assert.assertNotNull(trainService.addTrain(trainDto));
    }

    @Test
    public void testGetTrainByNumber() {
        TrainDto trainDto = TrainDto.builder()
                .id(1L)
                .number(1)
                .originStation("Moscow")
                .destinationStation("Saint-Petersburg")
                .seats(100)
                .departureTime("30-05-2020 15:10")
                .arrivalTime("30-05-2020 20:00")
                .build();

        Mockito.when(trainDao.findByNumber(1)).thenReturn(train1Id1);
        Assert.assertEquals(trainService.getTrainByNumber(1), trainDto);
    }

    @Test
    public void testGetTrainList() {
        TrainDto trainDto1 = TrainDto.builder()
                .id(1L)
                .number(1)
                .originStation("Moscow")
                .destinationStation("Murmansk")
                .seats(100)
                .departureTime("30-05-2020 15:10")
                .arrivalTime("30-05-2020 23:10")
                .build();

        TrainDto trainDto2 = TrainDto.builder()
                .id(3L)
                .number(2)
                .originStation("Moscow")
                .destinationStation("Saint-Petersburg")
                .seats(100)
                .departureTime("30-05-2020 15:10")
                .arrivalTime("30-05-2020 20:00")
                .build();

        Mockito.when(trainDao.findAll()).thenReturn(Arrays.asList(train1Id1, train1Id2, train2Id3));
        Assert.assertEquals(trainService.getTrainList(), Arrays.asList(trainDto1, trainDto2));
    }

    @Test
    public void testGetTrainRoutByTrainNumber() {
        TrainStationDto trainStationDtoMoscow = TrainStationDto.builder()
                .station("Moscow")
                .departureTime("30-05-2020 15:10")
                .build();

        TrainStationDto trainStationDtoPetersburg = TrainStationDto.builder()
                .station("Saint-Petersburg")
                .arrivalTime("30-05-2020 20:00")
                .departureTime("30-05-2020 20:10")
                .build();

        TrainStationDto trainStationDtoMurmansk = TrainStationDto.builder()
                .station("Murmansk")
                .arrivalTime("30-05-2020 23:10")
                .build();

        Mockito.when(trainDao.findTrainListByNumber(1)).thenReturn(Arrays.asList(train1Id1, train1Id2));
        Assert.assertEquals(trainService.getTrainRoutByTrainNumber(1), Arrays.asList(trainStationDtoMoscow,
                trainStationDtoPetersburg, trainStationDtoMurmansk));
    }


    @Test
    public void testGetTrainListBetweenTwoPoints() {
        TrainDto trainDtoOutput = TrainDto.builder()
                .id(1L)
                .number(1)
                .originStation("Moscow")
                .destinationStation("Murmansk")
                .seats(100)
                .departureTime("30-05-2020 15:10")
                .arrivalTime("30-05-2020 23:10")
                .build();

        TrainDto trainDtoInput = TrainDto.builder()
                .originStation("Moscow")
                .destinationStation("Murmansk")
                .seats(100)
                .departureTime("2020-05-30T15:00")
                .arrivalTime("2020-05-30T23:30")
                .build();

        Mockito.when(stationService.getStationByName("Moscow")).thenReturn(stationMoscow);
        Mockito.when(stationService.getStationByName("Murmansk")).thenReturn(stationMurmansk);
        Mockito.when(trainDao.findByStationIdAtGivenTerm(1, 3, LocalDateTime.parse("2020-05-30T15:00"),
                LocalDateTime.parse("2020-05-30T23:30"))).thenReturn(Arrays.asList(train1Id1, train1Id2));
        Assert.assertEquals(trainService.getTrainListBetweenTwoPoints(trainDtoInput), Collections.singletonList(trainDtoOutput));
    }

    @Test
    public void testGetTrainByOriginStation() {
        TrainDto trainDto = TrainDto.builder()
                .id(1L)
                .number(1)
                .originStation("Moscow")
                .destinationStation("Saint-Petersburg")
                .seats(100)
                .departureTime("30-05-2020 15:10")
                .arrivalTime("30-05-2020 23:10")
                .build();

        Mockito.when(trainDao.findByOriginStation(1, "Moscow")).thenReturn(train1Id1);
        Assert.assertEquals(trainService.getTrainByOriginStation(trainDto).getOriginStation(), stationMoscow);
    }

    @Test
    public void testGetTrainByDestinationStation() {
        TrainDto trainDto = TrainDto.builder()
                .id(1L)
                .number(1)
                .originStation("Saint-Petersburg")
                .destinationStation("Murmansk")
                .seats(100)
                .departureTime("30-05-2020 15:10")
                .arrivalTime("30-05-2020 23:10")
                .build();

        Mockito.when(trainDao.findByDestinationStation(1, "Murmansk")).thenReturn(train1Id2);
        Assert.assertEquals(trainService.getTrainByDestinationStation(trainDto).getDestinationStation(), stationMurmansk);
    }

    @Test
    public void testGetTrainListByTrainsId() {
        Mockito.when(trainDao.findTrainListByTrainDepartureAndArrivalId(1, 1, 2))
                .thenReturn(Arrays.asList(train1Id1, train1Id2));
        Assert.assertEquals(trainService.getTrainListByTrainsId(train1Id1, train1Id2), Arrays.asList(train1Id1, train1Id2));
    }
}
