package com.tsystems.project.dao;

import com.tsystems.project.model.Passenger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PassengerDao extends AbstractDao<Passenger> {

    public PassengerDao() {
        super(Passenger.class);
    }

    public Passenger findByPersonalData(String firstName, String lastName, LocalDate birthDate) {
        String queryString = "SELECT p FROM Passenger p " +
                "WHERE upper(p.firstName) = :firstName and upper(p.secondName) = :lastName and p.birthDate = :birthDate";
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

    public List<Passenger> findAllPassengersByTrainNumber(int trainNumber) {
        String queryString = "SELECT p FROM Passenger p inner join Ticket t on p.id = t.passenger.id " +
                "where t.train.number = :trainNumber";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trainNumber", trainNumber);
        return query.getResultList();
    }
}
