package com.tsystems.javaschool.test;

import com.tsystems.javaschool.test.config.TestConfig;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.ScheduleService;
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
    ScheduleDao scheduleDao;

    private static Train train1;

    private static Station stationMoscow;

    private static Station stationPetersburg;

    private static Schedule scheduleDepartureTrain1;

    private static Schedule scheduleArrivalTrain1;

    private static Station setUpStation(String name, long id) {
        return Station.builder()
                .id(id)
                .name(name)
                .build();
    }

    private static void setUpTrain() {
        train1 = Train.builder()
                .id(1)
                .number(1)
                .originStation(stationMoscow)
                .destinationStation(stationPetersburg)
                .seats(100)
                .schedules(Arrays.asList(scheduleDepartureTrain1, scheduleArrivalTrain1))
                .build();
        train1.setSchedules(Arrays.asList(scheduleDepartureTrain1, scheduleArrivalTrain1));
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
        scheduleArrivalTrain1 = setUpScheduleArrival(1, LocalDateTime.parse("2020-05-30T20:00"), stationPetersburg);
        scheduleDepartureTrain1 = setUpScheduleDeparture(2, LocalDateTime.parse("2020-05-30T15:10"), stationMoscow);
        setUpTrain();
    }

    @Test
    public void testAddTrain() {
        //parameter in add train
        TrainDto trainDto = TrainDto.builder()
                .id(1)
                .number(1)
                .originStation("Moscow")
                .destinationStation("Saint-Petersburg")
                .seats(100)
                .departureTime("2020-05-30T15:10")
                .arrivalTime("2020-05-30T20:00")
                .build();

        Mockito.when(trainDao.create(any())).thenReturn(train1);
        when(scheduleDao.create(scheduleDepartureTrain1)).thenReturn(scheduleDepartureTrain1);
        when(scheduleDao.create(scheduleArrivalTrain1)).thenReturn(scheduleArrivalTrain1);
        Assert.assertNotNull(trainService.addTrain(trainDto));
    }

    @Test
    public void testGetTrainByNumber() {
        TrainDto trainDto = TrainDto.builder()
                .id(1)
                .number(1)
                .originStation("Moscow")
                .destinationStation("Saint-Petersburg")
                .seats(100)
                .departureTime("30-05-2020 15:10")
                .arrivalTime("30-05-2020 20:00")
                .build();

        Mockito.when(trainDao.findByNumber(1)).thenReturn(train1);
        Assert.assertEquals(trainService.getTrainByNumber(1), trainDto);
    }
}
