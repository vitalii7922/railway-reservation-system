package com.tsystems.project.dao;

import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Schedule;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PassengerDao extends AbstractDao<Passenger> {

    public PassengerDao() {
        super(Passenger.class);
    }

    public Passenger find(String firstName, String lastName, LocalDate birthDate) {
        String queryString = "SELECT p FROM Passenger p WHERE lower(p.firstName) = :firstName and " +
                "lower(p.secondName) = :lastName and p.birthDate = :birthDate";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("birthDate", birthDate);
        List<Passenger> passengers = query.getResultList();
        if (passengers.isEmpty()) {
            return null;
        } else {
            return passengers.get(0);
        }
    }
}
