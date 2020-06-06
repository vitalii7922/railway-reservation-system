package com.tsystems.javaschool.test.config;

import com.tsystems.project.converter.*;
import com.tsystems.project.dao.*;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.helper.TrainHelper;
import com.tsystems.project.sender.ScheduleSender;
import com.tsystems.project.sender.StationSender;
import com.tsystems.project.service.*;
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
    @Primary
    public TicketDao ticketDao() {
        return Mockito.mock(TicketDao.class);
    }

    @Bean
    @Primary
    public PassengerDao passengerDao() {
        return Mockito.mock(PassengerDao.class);
    }

    @Bean
    TimeConverter timeConverter() {
        return new TimeConverter();
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
                scheduleService(),
                new TimeConverter(), new TrainHelper(new TrainMapper(new TimeConverter()), new TimeConverter()));
    }

    @Bean
    TicketService ticketService() {
        return new TicketService(ticketDao(), new PassengerService(passengerDao(), new PassengerMapper()),
                trainService(), new TicketMapper(new TimeConverter()));
    }

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean localEmfBean =
                new LocalEntityManagerFactoryBean();
        localEmfBean.setPersistenceUnitName("hibernateSpring");
        return localEmfBean;
    }
}
