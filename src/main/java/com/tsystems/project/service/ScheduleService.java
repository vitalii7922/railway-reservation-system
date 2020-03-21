package com.tsystems.project.service;

import com.mysql.cj.xdevapi.Collection;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
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
        return scheduleDao.findByTrainId(id);
    }


    public List<Schedule> getAllSchedules() {
        return scheduleDao.findAll();
    }

    public List<Schedule> getSchedulesByTrainsId(List<Train> trains) {
        return trains.stream()
                .map(train -> scheduleDao.findByTrainId(train.getId()))
                .collect(Collectors.toList());
    }
}
