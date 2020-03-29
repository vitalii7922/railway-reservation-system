package com.tsystems.project.service;

import com.tsystems.project.dao.TicketDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.metal.MetalIconFactory;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketDao ticketDao;

    @Transactional
    public Ticket editTicket(Ticket ticket) throws RuntimeException {
        ticketDao.update(ticket);
        return ticketDao.findOne(ticket.getId());
    }

    @Transactional
    public Ticket getTicketByPassenger(int trainNumber, String firstName, String lastName, LocalDate birthDate) {
        Ticket ticket = ticketDao.findByPassenger(trainNumber, firstName, lastName, birthDate);
        return ticket;
    }

    @Transactional
    public Ticket addTicket(Train train, Passenger passenger) {
        Ticket ticket = new Ticket();
        ticket.setTrain(train);
        ticket.setPassenger(passenger);
        return ticketDao.create(ticket);
    }
}
