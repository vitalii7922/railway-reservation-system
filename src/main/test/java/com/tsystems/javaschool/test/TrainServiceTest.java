package com.tsystems.javaschool.test;

import static org.hamcrest.CoreMatchers.is;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.TrainService;
import org.junit.Assert;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class TrainServiceTest {

    @Mock
    private TrainDao trainDaoMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private TrainService trainService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTrain() {
        when(trainDaoMock.create(any(Train.class))).thenReturn(new Train());
        Station originStation = new Station();
        originStation.setId(1);
        originStation.setName("Moscow");
        Station destinationStation = new Station();
        destinationStation.setId(2);
        destinationStation.setName("Saint-Petersburg");

        when(modelMapperMock.map(trainDaoMock.create(new Train()), TrainDto.class)).thenReturn(new TrainDto());
        assertThat(trainService.addTrain(1, originStation, destinationStation,
                100), is(notNullValue()));
        resetMocks();
    }

    @Test
    public void testGetTrain() {
        Train train = new Train();
        train.setNumber(1);
        when(trainDaoMock.findByNumber(1)).thenReturn(train);
        TrainDto trainDto = new TrainDto();
        trainDto.setNumber(1);
        when(modelMapperMock.map(trainDaoMock.findByNumber(1), TrainDto.class)).thenReturn(trainDto);

        Assert.assertEquals(1, trainService.getTrainByNumber(1).getNumber());
        resetMocks();
    }

    public void resetMocks() {
        Mockito.reset(trainDaoMock, modelMapperMock);
    }

}
