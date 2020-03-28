package com.tsystems.project.dao;

import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Ticket;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TicketDao extends AbstractDao<Ticket> {
    public TicketDao() {
        super(Ticket.class);
    }

    public Ticket findByPassenger(int trainNumber, long stationA, String firstName, String lastName, LocalDate birthDate) {
        String queryString = "SELECT t FROM Ticket t WHERE LOWER(t.passenger.firstName) = :firstName and LOWER(t.passenger.birthDate) = :birthDate " +
                            "and LOWER(t.passenger.secondName) = :lastName and t.train.originStation.id = :stationA and t.train.number = :trainNumber";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trainNumber", trainNumber);
        query.setParameter("stationA", stationA);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("birthDate", birthDate);
        List<Ticket> tickets = query.getResultList();
        if (tickets.isEmpty()) {
            return null;
        } else {
            return tickets.get(0);
        }
    }
}
