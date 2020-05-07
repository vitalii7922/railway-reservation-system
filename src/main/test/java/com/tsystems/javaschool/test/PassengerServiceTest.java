package com.tsystems.javaschool.test;

import com.tsystems.project.converter.PassengerConverter;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.model.Passenger;
import com.tsystems.project.service.PassengerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import java.time.LocalDate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class PassengerServiceTest {

    @Mock
    PassengerDao passengerDao;

    @Mock
    PassengerConverter passengerConverter;

    @InjectMocks
    PassengerService passengerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddPassenger() {
        PassengerTrainDto passengerTrainDto = new PassengerTrainDto();
        passengerTrainDto.setFirstName("Ivan");
        passengerTrainDto.setSecondName("Ivanov");
        passengerTrainDto.setBirthDate("1997-12-01");

        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setFirstName("Ivan");
        passengerDto.setSecondName("Ivanov");
        passengerDto.setBirthDate(LocalDate.parse("1997-12-01"));

        Passenger passenger = new Passenger();
        passenger.setFirstName("Ivan");
        passenger.setSecondName("Ivanov");
        passenger.setBirthDate(LocalDate.parse("1997-12-01"));

        when(passengerDao.create(passenger)).thenReturn(passenger);
        when(passengerConverter.convertToPassengerDto(passengerDao.create(passenger))).thenReturn(passengerDto);
        assertThat(passengerService.addPassenger(passengerTrainDto), is(notNullValue()));
    }
}
