package com.tsystems.project.service;

import com.tsystems.project.dao.StationDao;
import com.tsystems.project.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StationService {

    @Autowired
    StationDao stationDao;

    @Transactional
    public Station addStation(String name) {
        if (!name.equals("") && stationDao.findByName(name) == null) {
            Station station = new Station();
            station.setName(name);
            stationDao.create(station);
            return station;
        } else {
            return null;
        }
    }


    public Station getStationById(long id){
        return stationDao.findOne(id);
    }

    public Station getStation(String stationName) {
       return stationDao.findByName(stationName);
    }

    @Transactional
    public Station editStation(Station station) {
        return  stationDao.update(station);
    }

    public void removeStation(Station station) {
        stationDao.delete(station);
    }

    public List<Station> getAllStations() {
        return stationDao.findAll();
    }
}
