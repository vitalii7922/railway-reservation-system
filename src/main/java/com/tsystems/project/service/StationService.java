package com.tsystems.project.service;

import com.tsystems.project.dao.StationDao;
import com.tsystems.project.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class StationService {

    @Autowired
    StationDao stationDao;

    @Transactional
    public Station addStation(String name) {
        stationDao.getCurrentSession().beginTransaction();
        if (!name.equals("") && stationDao.findByName(name) == null) {
            Station station = new Station();
            station.setName(name);
            stationDao.create(station);
            stationDao.getCurrentSession().getTransaction().commit();
            stationDao.getCurrentSession().close();
            return station;
        } else {
            stationDao.getCurrentSession().getTransaction().commit();
            stationDao.getCurrentSession().close();
            return null;
        }
    }


    public Station getStationById(long id){
        stationDao.getCurrentSession().beginTransaction();
        Station station = stationDao.findOne(id);
        stationDao.getCurrentSession().getTransaction().commit();
        stationDao.getCurrentSession().close();
        return station;
    }

    public Station getStation(String stationName) {
        stationDao.getCurrentSession().beginTransaction();
        Station station = stationDao.findByName(stationName);
        stationDao.getCurrentSession().getTransaction().commit();
        stationDao.getCurrentSession().close();
        return station;
    }

    @Transactional
    public Station editStation(Station station) {
        return  stationDao.update(station);
    }

    public void removeStation(Station station) {
        stationDao.delete(station);
    }

    public List<Station> getAllStations() {
        stationDao.getCurrentSession().beginTransaction();
        List<Station> stations = stationDao.findAll();
        stationDao.getCurrentSession().getTransaction().commit();
        stationDao.getCurrentSession().close();
        return stations;
    }
}
