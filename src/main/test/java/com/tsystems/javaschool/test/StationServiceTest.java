package com.tsystems.javaschool.test;

import com.tsystems.javaschool.test.config.TestConfig;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.dto.StationDto;
import com.tsystems.project.service.StationService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class StationServiceTest {

    @Autowired
    private StationDao stationDaoMock;

    @Autowired
    private StationService stationService;

    private static Station stationMoscow;

    private static Station stationPetersburg;

    private static Station setUpStation(String name, long id) {
        return Station.builder()
                .id(id)
                .name(name)
                .build();
    }


    @BeforeClass
    public static void setUp() {
        stationMoscow = setUpStation("Moscow", 1);
        stationPetersburg = setUpStation("Saint-Petersburg", 2);
    }

    @Test
    public void testAddStation() {
        when(stationDaoMock.findByName("Moscow")).thenReturn(null);
        when(stationDaoMock.create(any())).thenReturn(stationMoscow);
        Assert.assertNotNull(stationService.addStation("Moscow").getName());
    }

    @Test
    public void testGetAllStations() {
        StationDto stationDtoMoscow = StationDto.builder()
                .id(1)
                .name("Moscow")
                .build();

        StationDto stationDtoPetersburg = StationDto.builder()
                .id(2)
                .name("Saint-Petersburg")
                .build();

        when(stationDaoMock.findAll()).thenReturn(Arrays.asList(stationMoscow, stationPetersburg));
        Assert.assertThat(stationService.getAllStations().size(), is(2));
        Assert.assertEquals(Arrays.asList(stationDtoMoscow, stationDtoPetersburg), stationService.getAllStations());
    }

    @Test
    public void testGetStationById() {
        when(stationDaoMock.findOne(1)).thenReturn(stationMoscow);
        Assert.assertEquals(1, stationService.getStationById(1).getId());
    }

    @Test
    public void testGetStationByName() {
        when(stationDaoMock.findByName("Moscow")).thenReturn(stationMoscow);
        Assert.assertEquals("Moscow", stationService.getStationByName("Moscow").getName());
    }
}
