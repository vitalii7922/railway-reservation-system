/*
package com.tsystems.javaschool.test;
import com.tsystems.project.converter.ScheduleConverter;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.service.ScheduleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@Component
public class ScheduleServiceTest {
    @Mock
    ScheduleDao scheduleDaoMock;

    @Mock
    ScheduleConverter scheduleConverterMock;


    TimeConverter timeConverter;

    @InjectMocks
    ScheduleService scheduleService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        timeConverter = new TimeConverter();
    }

    @Test
    public void testGetScheduleByStationId() {
        //initialize schedule, train, stations
        Schedule scheduleDeparture = new Schedule();
        scheduleDeparture.setDepartureTime(LocalDateTime.parse("2020-01-01T10:00"));

        Train train = new Train();
        train.setId(1);
        train.setNumber(1);

        Station originStation = new Station("Moscow");
        originStation.setId(1);
        Station destinationStation = new Station("Saint-Petersburg");
        destinationStation.setId(2);

        scheduleDeparture.setStation(originStation);
        scheduleDeparture.setTrain(train);

        train.setOriginStation(originStation);
        train.setDestinationStation(destinationStation);

        List<Schedule> scheduleList = new ArrayList<>() {
            {
                add(scheduleDeparture);
            }
        };

        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setDepartureTime("01-01-2020 10:00");
        when(scheduleDaoMock.findByStationId(1)).thenReturn(scheduleList);
        when(scheduleConverterMock.convertToScheduleDto(scheduleList.get(0))).thenReturn(scheduleDto);

        assertEquals("01-01-2020 10:00", scheduleService.getSchedulesByStationId(1).get(0).getDepartureTime());
    }

    @Test
    public void testGetTodaySchedulesByStationId() {
        //initialize schedule, train, stations
        Schedule scheduleDeparture = new Schedule();
        scheduleDeparture.setDepartureTime(LocalDateTime.now());

        Train train = new Train();
        train.setId(1);
        train.setNumber(1);

        Station originStation = new Station("Moscow");
        originStation.setId(1);
        Station destinationStation = new Station("Saint-Petersburg");
        destinationStation.setId(2);

        scheduleDeparture.setStation(originStation);
        scheduleDeparture.setTrain(train);

        train.setOriginStation(originStation);
        train.setDestinationStation(destinationStation);

        List<Schedule> scheduleList = new ArrayList<>() {
            {
                add(scheduleDeparture);
            }
        };

        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setDepartureTime(timeConverter.convertDateTime(LocalDateTime.now()));

        when(scheduleDaoMock.findByStationId(1)).thenReturn(scheduleList);
        when(scheduleConverterMock.convertToScheduleDto(scheduleList.get(0))).thenReturn(scheduleDto);

        assertEquals(timeConverter.reversedConvertDateTime(scheduleService
                .getSchedulesByStationId(1).get(0).getDepartureTime()).toLocalDate(),
                LocalDate.now());
    }


    @After
    public void resetMocks() {
        Mockito.reset(scheduleDaoMock, scheduleConverterMock);
    }

}
*/
