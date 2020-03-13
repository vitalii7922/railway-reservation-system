package com.tsystems.project.service;

import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketDao ticketDao;

    @Transactional
    public Ticket addTicket(Ticket ticket) {
        ticketDao.create(ticket);
        return ticket;
    }

    @Transactional
    public Ticket editTicket(Ticket ticket) throws RuntimeException {
        ticketDao.update(ticket);
        return ticketDao.findOne(ticket.getId());
    }

    @Transactional
    public void removeTicket(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketDao.findAll();
    }
}
