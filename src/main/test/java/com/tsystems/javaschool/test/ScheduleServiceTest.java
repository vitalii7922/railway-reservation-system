package com.tsystems.javaschool.test;
import com.tsystems.javaschool.test.config.TestConfig;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.service.ScheduleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ScheduleServiceTest {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void testGetSchedulesByStationId() {
        Station station = Station.builder()
                .id(1)
                .name("Moscow")
                .build();

        //initialize arrival time train number 1
        Schedule scheduleArrivalTrain1 = Schedule.builder()
                .id(1)
                .arrivalTime(LocalDateTime.parse("2020-05-31T15:00"))
                .station(station)
                .build();

        //initialize departure time train number 1
        Schedule scheduleDepartureTrain1 = Schedule.builder()
                .id(2)
                .departureTime(LocalDateTime.parse("2020-05-31T15:10"))
                .station(station)
                .build();

        //initialize train number 1 to Moscow
        Train trainId1 = Train.builder()
                .id(1)
                .number(1)
                .destinationStation(station)
                .schedules(Collections.singletonList(scheduleArrivalTrain1))
                .build();

        //initialize train number 1 from Moscow
        Train trainId2 = Train.builder()
                .id(2)
                .number(1)
                .originStation(station)
                .schedules(Collections.singletonList(scheduleDepartureTrain1))
                .build();

        //initialize schedule by train number 1
        scheduleArrivalTrain1.setTrain(trainId1);
        scheduleDepartureTrain1.setTrain(trainId2);


        //initialize departure time train number 2
        Schedule scheduleDepartureTrain2 = Schedule.builder()
                .id(3)
                .departureTime(LocalDateTime.parse("2020-05-31T15:10"))
                .station(station)
                .build();

        //initialize train number 2 from Moscow
        Train trainId3 = Train.builder()
                .id(3)
                .number(2)
                .originStation(station)
                .schedules(Collections.singletonList(scheduleDepartureTrain2))
                .build();

        scheduleDepartureTrain2.setTrain(trainId3);

        //initialize departure time train number 3
        Schedule scheduleArrivalTrain3 = Schedule.builder()
                .id(4)
                .arrivalTime(LocalDateTime.parse("2020-05-31T15:30"))
                .station(station)
                .build();

        //initialize train number 3 to Moscow
        Train trainId4 = Train.builder()
                .id(4)
                .number(3)
                .destinationStation(station)
                .schedules(Collections.singletonList(scheduleArrivalTrain3))
                .build();
        //initialize schedule train 3
        scheduleArrivalTrain3.setTrain(trainId4);

        when(scheduleDao.findByStationId(1)).thenReturn(Arrays.asList(scheduleArrivalTrain1, scheduleDepartureTrain1,
                scheduleDepartureTrain2, scheduleArrivalTrain3));

        ScheduleDto scheduleDtoMoscowTrain1 = ScheduleDto.builder()
                .departureTime("31-05-2020 15:10")
                .arrivalTime("31-05-2020 15:00")
                .trainNumber(1)
                .id(1)
                .stationName("Moscow")
                .trainId(1)
                .build();

        ScheduleDto scheduleDtoMoscowTrain2 = ScheduleDto.builder()
                .departureTime("31-05-2020 15:10")
                .trainNumber(2)
                .id(3)
                .stationName("Moscow")
                .trainId(3)
                .build();

        ScheduleDto scheduleDtoMoscowTrain3 = ScheduleDto.builder()
                .arrivalTime("31-05-2020 15:30")
                .trainNumber(3)
                .id(4)
                .stationName("Moscow")
                .trainId(4)
                .build();

        Assert.assertEquals(Arrays.asList(scheduleDtoMoscowTrain1, scheduleDtoMoscowTrain2, scheduleDtoMoscowTrain3),
                scheduleService.getSchedulesByStationId(1));
    }

    @Test
    public void testGetTodaySchedulesByStationId() {
        Station station = Station.builder()
                .id(1)
                .name("Moscow")
                .build();

        //initialize arrival time train number 1
        Schedule scheduleArrivalTrain1 = Schedule.builder()
                .id(1)
                .arrivalTime(LocalDateTime.parse("2020-05-30T15:00"))
                .station(station)
                .build();

        //initialize departure time train number 1
        Schedule scheduleDepartureTrain1 = Schedule.builder()
                .id(2)
                .departureTime(LocalDateTime.parse("2020-05-30T15:10"))
                .station(station)
                .build();

        //initialize train number 1 to Moscow
        Train trainId1 = Train.builder()
                .id(1)
                .number(1)
                .destinationStation(station)
                .schedules(Collections.singletonList(scheduleArrivalTrain1))
                .build();

        //initialize train number 1 from Moscow
        Train trainId2 = Train.builder()
                .id(2)
                .number(1)
                .originStation(station)
                .schedules(Collections.singletonList(scheduleDepartureTrain1))
                .build();

        //initialize schedule by train number 1
        scheduleArrivalTrain1.setTrain(trainId1);
        scheduleDepartureTrain1.setTrain(trainId2);
        //---------------------------------------
        //initialize departure time train number 2
        Schedule scheduleDepartureTrain2 = Schedule.builder()
                .id(3)
                .departureTime(LocalDateTime.now())
                .station(station)
                .build();

        //initialize train number 2 from Moscow
        Train trainId3 = Train.builder()
                .id(3)
                .number(2)
                .originStation(station)
                .schedules(Collections.singletonList(scheduleDepartureTrain2))
                .build();

        scheduleDepartureTrain2.setTrain(trainId3);
        //----------------------------------------
        //initialize departure time train number 3
        Schedule scheduleArrivalTrain3 = Schedule.builder()
                .id(4)
                .arrivalTime(LocalDateTime.now())
                .station(station)
                .build();

        //initialize train number 3 to Moscow
        Train trainId4 = Train.builder()
                .id(4)
                .number(3)
                .destinationStation(station)
                .schedules(Collections.singletonList(scheduleArrivalTrain3))
                .build();
        //initialize schedule train 3
        scheduleArrivalTrain3.setTrain(trainId4);
        //----------------------------------------
        //initialize departure time train number 4
        Schedule scheduleArrivalTrain4 = Schedule.builder()
                .id(5)
                .arrivalTime(LocalDateTime.parse("2020-05-30T15:10"))
                .station(station)
                .build();

        //initialize train number 4 to Moscow
        Train trainId5 = Train.builder()
                .id(5)
                .number(4)
                .destinationStation(station)
                .schedules(Collections.singletonList(scheduleArrivalTrain3))
                .build();
        //initialize schedule train 4
        scheduleArrivalTrain4.setTrain(trainId5);
        //----------------------------------------
        //initialize departure time train number 5
        Schedule scheduleDepartureTrain5 = Schedule.builder()
                .id(6)
                .departureTime(LocalDateTime.parse("2020-05-30T15:00"))
                .station(station)
                .build();

        //initialize train number 5 from Moscow
        Train trainId6 = Train.builder()
                .id(6)
                .number(5)
                .originStation(station)
                .schedules(Collections.singletonList(scheduleDepartureTrain5))
                .build();
        //initialize schedule train 5
        scheduleDepartureTrain5.setTrain(trainId6);
        //--------------------------------------
        when(scheduleDao.findByStationId(1)).thenReturn(Arrays.asList(scheduleArrivalTrain1, scheduleDepartureTrain1,
                scheduleDepartureTrain2, scheduleArrivalTrain3, scheduleArrivalTrain4, scheduleDepartureTrain5));

        //actual result of list is 2 elements(schedules of train 2 and train 3)
        Assert.assertThat(scheduleService.getTodaySchedulesByStationId(1).size(), is(2));
    }


}
