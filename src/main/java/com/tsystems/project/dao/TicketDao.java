package com.tsystems.project.dao;

import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.model.Ticket;
import com.tsystems.project.dto.PassengerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * author Vitalii Nefedov
 */
@Repository
public class TicketDao extends AbstractDao<Ticket> {
    public TicketDao() {
        super(Ticket.class);
    }

    /**
     * find a ticket by a train id and a passenger id
     *
     * @param trainDto     train data
     * @param passengerDto passenger data
     * @return ticket
     */
    public Ticket findByPassenger(TrainDto trainDto, PassengerDto passengerDto) {
        String queryString = "SELECT ticket FROM Ticket ticket INNER JOIN Train train ON ticket.train.id = train.id where " +
                "train.id = :trainId and ticket.passenger.id = :passengerId";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trainId", trainDto.getId());
        query.setParameter("passengerId", passengerDto.getId());
        List<Ticket> tickets = query.getResultList();
        if (tickets.isEmpty()) {
            return null;
        } else {
            return tickets.get(0);
        }
    }
}
