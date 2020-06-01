package com.tsystems.javaschool.test.config;

import com.tsystems.project.converter.ScheduleMapper;
import com.tsystems.project.converter.StationMapper;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.converter.TrainMapper;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.helper.TrainHelper;
import com.tsystems.project.sender.ScheduleSender;
import com.tsystems.project.sender.StationSender;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public StationDao stationDao() {
        return Mockito.mock(StationDao.class);
    }

    @Bean
    @Primary
    public ScheduleDao scheduleDao() {
        return Mockito.mock(ScheduleDao.class);
    }

    @Bean
    @Primary
    public TrainDao trainDao() {
        return Mockito.mock(TrainDao.class);
    }

    @Bean
    StationService stationService() {
        return new StationService(stationDao(), new StationMapper(), new StationSender());
    }

    @Bean
    ScheduleService scheduleService() {
        return new ScheduleService(scheduleDao(), new ScheduleMapper(new TimeConverter()), new TimeConverter(),
                new ScheduleSender());
    }

    @Bean
    TrainService trainService() {
        return new TrainService(trainDao(), stationService(), new TrainMapper(new TimeConverter()),
                Mockito.mock(ScheduleService.class),
                new TimeConverter(), new TrainHelper(new TrainMapper(new TimeConverter()), new TimeConverter()));
    }

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean localEmfBean =
                new LocalEntityManagerFactoryBean();
        localEmfBean.setPersistenceUnitName("hibernateSpring");
        return localEmfBean;
    }
}
