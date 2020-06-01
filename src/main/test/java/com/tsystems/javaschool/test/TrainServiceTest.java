package com.tsystems.javaschool.test;
import com.tsystems.javaschool.test.config.TestConfig;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.TrainService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class TrainServiceTest {

    @Autowired
    private TrainDao trainDao;

    @Autowired
    private TrainService trainService;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void testAddTrain() {
        Station stationMoscow = Station.builder()
                .id(1)
                .name("Moscow")
                .build();

        Station stationPetersburg = Station.builder()
                .id(2)
                .name("Saint-Petersburg")
                .build();

        Train train = Train.builder()
                .number(1)
                .originStation(stationMoscow)
                .destinationStation(stationPetersburg)
                .seats(100)
                .build();

        TrainDto trainDto = TrainDto.builder()
                .id(1)
                .number(1)
                .departureTime("2020-05-30T15:10")
                .arrivalTime("2020-05-30T20:00")
                .originStation("Moscow")
                .destinationStation("Saint-Petersburg")
                .seats(100)
                .build();

        //initialize schedule arrival time train number 1
        Schedule scheduleArrival = Schedule.builder()
                .id(1)
                .arrivalTime(LocalDateTime.parse("2020-05-30T20:00"))
                .station(stationMoscow)
                .build();

        //initialize schedule departure time train number 1
        Schedule scheduleDeparture = Schedule.builder()
                .id(2)
                .departureTime(LocalDateTime.parse("2020-05-30T15:10"))
                .station(stationPetersburg)
                .build();

        Mockito.when(trainDao.create(any())).thenReturn(train);
        Mockito.when(scheduleService.addSchedule(train, LocalDateTime.parse(trainDto.getDepartureTime()),
                LocalDateTime.parse(trainDto.getArrivalTime())))
                .thenReturn(Arrays.asList(scheduleDeparture, scheduleArrival));
        Assert.assertNotNull(trainService.addTrain(trainDto));
    }
}
