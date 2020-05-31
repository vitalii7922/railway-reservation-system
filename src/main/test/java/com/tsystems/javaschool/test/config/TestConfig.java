package com.tsystems.javaschool.test.config;

import com.tsystems.project.converter.ScheduleConverter;
import com.tsystems.project.converter.StationConverter;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.sender.ScheduleSender;
import com.tsystems.project.sender.StationSender;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
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
    StationService stationService() {
        return new StationService(stationDao(), new StationConverter(), new StationSender());
    }

    @Bean
    ScheduleService scheduleService() {
        return new ScheduleService(scheduleDao(), new ScheduleConverter(new TimeConverter()), new TimeConverter(),
                new ScheduleSender());
    }

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean localEmfBean =
                new LocalEntityManagerFactoryBean();
        localEmfBean.setPersistenceUnitName("hibernateSpring");
        return localEmfBean;
    }
}
