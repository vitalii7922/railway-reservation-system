package com.tsystems.project.dao;

import com.tsystems.project.domain.Station;
import org.springframework.stereotype.Repository;

@Repository
public class StationDao extends AbstractDao<Station> {

    public StationDao() {
        super(Station.class);
    }
}
