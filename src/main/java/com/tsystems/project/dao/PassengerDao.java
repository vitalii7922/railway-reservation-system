package com.tsystems.project.dao;

import com.tsystems.project.domain.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerDao extends AbstractDao<Passenger> {

    public PassengerDao() {
        super(Passenger.class);
    }
}
