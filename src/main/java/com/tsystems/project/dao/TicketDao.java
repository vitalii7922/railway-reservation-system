package com.tsystems.project.dao;

import com.tsystems.project.domain.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDao extends AbstractDao<Ticket> {
    public TicketDao() {
        super(Ticket.class);
    }
}
