package com.tsystems.project.service;

import com.tsystems.project.dao.StationDao;
import com.tsystems.project.model.Station;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class StationService {

    @Autowired
    StationDao stationDao;

    @Autowired
    ModelMapper modelMapper;

    private static final Log log = LogFactory.getLog(TicketService.class);

    @Transactional
    public Station addStation(String name)  {
        Station station = new Station();
        if (!name.matches("\\s*") && stationDao.findByName(name) == null) {
            station.setName(name.toUpperCase());
            log.info("--------Station has been added-------------");
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
        List<Station> stations = stationDao.findAll();
        Collections.sort(stations);
        return stations;
    }
}
