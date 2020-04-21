package com.tsystems.project.dao;

import com.tsystems.project.model.Ticket;
import com.tsystems.project.dto.PassengerDto;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TicketDao extends AbstractDao<Ticket> {
    public TicketDao() {
        super(Ticket.class);
    }

    public Ticket findByPassenger(int trainNumber, PassengerDto passenger) {
        String queryString = "SELECT t FROM Ticket t WHERE t.passenger.id = :passengerId and t.train.number = :trainNumber";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trainNumber", trainNumber);
        query.setParameter("passengerId", passenger.getId());
        List<Ticket> tickets = query.getResultList();
        if (tickets.isEmpty()) {
            return null;
        } else {
            return tickets.get(0);
        }
    }
}
