package com.tsystems.javaschool.test;

import com.tsystems.project.service.PassengerService;
import org.junit.Before;
import org.mockito.*;

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
