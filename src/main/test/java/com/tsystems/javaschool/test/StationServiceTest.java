package com.tsystems.javaschool.test;
import com.tsystems.project.converter.StationConverter;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.sender.StationSender;
import com.tsystems.project.service.StationService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-config.xml")
public class StationServiceTest {

    @Mock
    StationDao stationDaoMock;

    @Autowired
    @InjectMocks
    StationService stationService;

  /*  @Spy
    StationConverter stationConverter;

    @Spy
    StationSender sender;*/

   /* @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }*/

    @Test
    public void testAddStation() {
        Station station = new Station();
        station.setName("Moscow");
        when(stationDaoMock.create(station)).thenReturn(station);
        Assert.assertNotNull(stationService.addStation("Moscow"));
        assertThat(stationService.addStation("Moscow"), is(notNullValue()));
    }

    @After
    public void resetMocks() {
        Mockito.reset(stationDaoMock);
    }

}
