package com.tsystems.javaschool.test;
import com.tsystems.javaschool.test.config.TestConfig;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.dto.StationDto;
import com.tsystems.project.service.StationService;
import org.junit.Assert;
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

    @Test
    public void testAddStation() {
        Station station = Station.builder()
                .id(1)
                .name("Moscow")
                .build();

        when(stationDaoMock.findByName("Moscow")).thenReturn(null);
        when(stationDaoMock.create(any())).thenReturn(station);
        Assert.assertNotNull(stationService.addStation("Moscow").getName());
    }

    @Test
    public void testGetAllStations() {
        Station stationMoscow = Station.builder()
                .id(1)
                .name("Moscow")
                .build();

        Station stationPetersburg = Station.builder()
                .id(2)
                .name("Saint-Petersburg")
                .build();

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
        Station stationMoscow = Station.builder()
                .id(1)
                .name("Moscow")
                .build();

        when(stationDaoMock.findOne(1)).thenReturn(stationMoscow);
        Assert.assertEquals(1, stationService.getStationById(1).getId());
    }

    @Test
    public void testGetStationByName() {
        Station stationMoscow = Station.builder()
                .id(1)
                .name("Moscow")
                .build();

        when(stationDaoMock.findByName("Moscow")).thenReturn(stationMoscow);
        Assert.assertEquals("Moscow", stationService.getStationByName("Moscow").getName());
    }
}
