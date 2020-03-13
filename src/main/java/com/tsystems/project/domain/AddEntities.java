package com.tsystems.project.domain;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddEntities {
    private static Session sessionObj;
    private static SessionFactory sessionFactoryObj;

    private static SessionFactory buildSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File and add annotated class
        Configuration configObj = new Configuration().configure().addAnnotatedClass(Passenger.class)
                .addAnnotatedClass(Ticket.class)
                .addAnnotatedClass(Train.class)
                .addAnnotatedClass(Station.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Schedule.class);
        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }

    public static void createRecord() {
        Passenger passengerObj = null;
        Station station = null;
        Train train1 = null;
        Train train2 = null;
        Ticket ticket = null;
        User user = null;
        Schedule schedule = null;

        // Getting Session Object From SessionFactory
        sessionObj = buildSessionFactory().openSession();
        // Getting Transaction Object From Session Object
        sessionObj.beginTransaction();

        // Creating Transaction Entities
        passengerObj = new Passenger();
        ticket = new Ticket();
        train1 = new Train();
        station = new Station();
        user = new User();
        schedule = new Schedule();

        List<Train> trains = new ArrayList<Train>();
        trains.add(train1);

        //initializing objects

            train1.setId(1);
            train1.setNumber(12);
            train1.setSeats(100);
            train1.setDepartureStation(station);
            train1.setOriginStation(station);


        station.setName("Moscow railway station");


        List<Station> stations = new ArrayList<Station>();
//            stations.add(station);
//            train1.setStations(stations);
//            train2.setStations(stations);
//
//            ticket.setTrains(trains);

        passengerObj.setFirstName("Vasilii");
        passengerObj.setSecondName("Vasiliev");
        passengerObj.setBirthDate(LocalDate.of(1990, 12, 01));
        ticket.setPassenger(passengerObj);

        user.setLogin("login");
        user.setLogin("password");

        sessionObj.save(ticket);
        sessionObj.save(passengerObj);
        sessionObj.save(train1);
        sessionObj.save(station);
        sessionObj.save(user);
        sessionObj.save(schedule);
        // Committing the Transactions to the Database
        sessionObj.getTransaction().commit();
    }
}
