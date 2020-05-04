package com.tsystems.javaschool.test;

import com.tsystems.project.converter.StationConverter;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.model.Station;
import com.tsystems.project.sender.StationSender;
import com.tsystems.project.service.StationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class StationServiceTest {

    @Mock
    StationDao stationDao;

    @InjectMocks
    StationService stationService;

    @Spy
    StationConverter stationConverter;

    @Mock
    StationSender stationSender;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddStation() {
        Station station = new Station();
        station.setName("Moscow");
        when(stationDao.create(station)).thenReturn(station);
        assertThat(stationService.addStation("Moscow"), is(notNullValue()));
    }

    @After
    public void resetMocks() {
        Mockito.reset(stationDao, stationSender, stationConverter);
    }

}
