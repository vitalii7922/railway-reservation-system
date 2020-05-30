package com.tsystems.javaschool.test.config;

import com.tsystems.project.converter.StationConverter;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.sender.StationSender;
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
    StationService stationService() {
        return new StationService(stationDao(), new StationConverter(), new StationSender());
    }

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean localEmfBean =
                new LocalEntityManagerFactoryBean();
        localEmfBean.setPersistenceUnitName("hibernateSpring");
        return localEmfBean;
    }
}
