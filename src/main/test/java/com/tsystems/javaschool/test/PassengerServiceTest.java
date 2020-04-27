package com.tsystems.javaschool.test;

import com.tsystems.project.model.Station;
import com.tsystems.project.service.PassengerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class PassengerServiceTest {


    @InjectMocks
    PassengerService passengerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

 /*   @Test
    public void testGetPassengers() {
        Station station = new Station();
        station.setName("Moscow");
        when(stationDao.create(station)).thenReturn(station);
        assertThat(stationService.addStation("Moscow"), is(notNullValue()));
        resetMocks();
    }

    public void resetMocks() {
        Mockito.reset(stationDao, sender, stationConverter);
    }*/
}
