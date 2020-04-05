package com.tsystems.project.service;

import com.tsystems.project.dao.StationDao;
import com.tsystems.project.domain.Station;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StationService {

    @Autowired
    StationDao stationDao;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public Station addStation(String name) {
        Station station = null;
        if (!name.matches("\\s*") && stationDao.findByName(name) == null) {
            station = new Station();
            station.setName(name);
            return stationDao.create(station);
        }
        return station;
    }

    public Station getStationById(long id){
        return stationDao.findOne(id);
    }

    public Station getStationByName(String stationName) {
        return stationDao.findByName(stationName);
    }


    public List<Station> getAllStations() {
        return stationDao.findAll();
    }
}
