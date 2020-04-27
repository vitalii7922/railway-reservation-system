package com.tsystems.project.dao;

import com.tsystems.project.dto.TrainDto;
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

    public Ticket findByPassenger(TrainDto trainDto, PassengerDto passenger) {
        String queryString = "SELECT t FROM Ticket t INNER JOIN Train train ON t.train.id = train.id where train.id = :trainId and t.passenger.id = :passengerId";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trainId", trainDto.getId());
        query.setParameter("passengerId", passenger.getId());
        List<Ticket> tickets = query.getResultList();
        if (tickets.isEmpty()) {
            return null;
        } else {
            return tickets.get(0);
        }
    }
}
