package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    TrainDao trainDao;

    @Autowired
    StationDao stationDao;

    @Transactional
    public void addSchedule(Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {

        Schedule scheduleDeparture = null;
        Schedule scheduleArrival = null;

        scheduleDeparture = new Schedule();
        scheduleDeparture.setTrain(train);
        scheduleDeparture.setDepartureTime(departureTime);
        scheduleDeparture.setStation(train.getOriginStation());
        scheduleDao.create(scheduleDeparture);

        scheduleArrival = new Schedule();
        scheduleArrival.setTrain(train);
        scheduleArrival.setArrivalTime(arrivalTime);
        scheduleArrival.setStation(train.getDestinationStation());
        scheduleDao.create(scheduleArrival);


    }

    @Transactional
    public Schedule editSchedule(Schedule schedule) {
        scheduleDao.update(schedule);
        return scheduleDao.findOne(schedule.getId());
    }

    @Transactional
    public void removeSchedule(Schedule schedule) {
        scheduleDao.delete(schedule);
    }

    @Transactional
    public Schedule getScheduleByTrainId(long id) {
        scheduleDao.getCurrentSession().beginTransaction();
        Schedule schedule = scheduleDao.findByTrainId(id);
        scheduleDao.getCurrentSession().getTransaction().commit();
        scheduleDao.getCurrentSession().close();
        return schedule;
    }


    public List<Schedule> getAllSchedules() {
        return scheduleDao.findAll();
    }

    public Schedule getSchedulesByTrainsArriveId(Train train) {
        scheduleDao.getCurrentSession().beginTransaction();
        Schedule schedule = scheduleDao.findByTrainArriveId(train.getId());
        scheduleDao.getCurrentSession().getTransaction().commit();
        scheduleDao.getCurrentSession().close();
        return schedule;
    }

    public Schedule getSchedulesByTrainsDepartureId(Train train) {
        scheduleDao.getCurrentSession().beginTransaction();
        Schedule schedule = scheduleDao.findByTrainArriveId(train.getId());
        scheduleDao.getCurrentSession().getTransaction().commit();
        scheduleDao.getCurrentSession().close();
        return schedule;
    }

    public Map<Schedule, Schedule> getSchedulesByTrains(Map<Train, Train> trains) {
        scheduleDao.getCurrentSession().beginTransaction();
        Map<Schedule, Schedule> map = new LinkedHashMap<>();
        for (Map.Entry<Train, Train> entry : trains.entrySet()) {
            map.put(scheduleDao.findByTrainDepartureId(entry.getKey().getId()), scheduleDao.findByTrainArriveId(entry.getValue().getId()));
        }
        scheduleDao.getCurrentSession().getTransaction().commit();
        scheduleDao.getCurrentSession().close();
        return map;
    }
}

