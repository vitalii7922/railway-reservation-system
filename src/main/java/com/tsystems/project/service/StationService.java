package com.tsystems.project.service;

import com.tsystems.project.dao.StationDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StationService {

    @Autowired
    StationDao stationDao;

    @Transactional
    public void addStation(Station station) {
        stationDao.create(station);
    }


    public Station getStationById(long id){
        return stationDao.findOne(id);
    }

    public Station getStation(String stationName) {
       return stationDao.findByName(stationName);
    }

    @Transactional
    public Station editStation(Station station) throws RuntimeException {
        return  stationDao.update(station);
    }


    public void removeStation(Station station) {
        stationDao.delete(station);
    }

    public List<Station> getAllStations() {
        return stationDao.findAll();
    }
}
