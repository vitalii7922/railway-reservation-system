package com.tsystems.project.dao;

import com.tsystems.project.domain.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

import javax.imageio.spi.ServiceRegistry;

@org.springframework.context.annotation.Configuration
class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
           Configuration configuration = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Passenger.class)
                    .addAnnotatedClass(Ticket.class)
                    .addAnnotatedClass(Train.class)
                    .addAnnotatedClass(Station.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Schedule.class);
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    @Bean
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
