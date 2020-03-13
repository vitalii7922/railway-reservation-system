package com.tsystems.project.service;

import com.tsystems.project.dao.StationDao;
import com.tsystems.project.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StationService {
    @Autowired
    StationDao stationDao;

    @Transactional
    public Station addStation(Station station) {
        stationDao.create(station);
        return station;
    }

    @Transactional
    public Station editStation(Station station) throws RuntimeException {
        stationDao.update(station);
        return stationDao.findOne(station.getId());
    }

    @Transactional
    public void removeStation(Station station) {
        stationDao.delete(station);
    }

    public List<Station> getAllStations() {
        return stationDao.findAll();
    }
}
