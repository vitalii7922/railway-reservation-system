package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    TrainDao trainDao;

    @Autowired
    StationDao stationDao;

    @Transactional
    public Schedule addSchedule(Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {

        Train trainArrival = trainDao.findByNumber(train.getNumber());
        Schedule schedule = scheduleDao.findByTrainId(trainArrival.getId());
        Duration duration = null;

        if(schedule != null) {
            duration = Duration.between(schedule.getArrivalTime(), departureTime);
        }

        if (duration != null && duration.toMinutes() < 5) {
            trainDao.delete(train);
            return null;
        }

            Schedule scheduleDeparture = new Schedule();
            scheduleDeparture.setTrain(train);
            scheduleDeparture.setDepartureTime(departureTime);
            scheduleDeparture.setStation(train.getOriginStation());
            scheduleDao.create(scheduleDeparture);

            Schedule scheduleArrival = new Schedule();
            scheduleArrival.setTrain(train);
            scheduleArrival.setArrivalTime(arrivalTime);
            scheduleArrival.setStation(train.getDestinationStation());
            return scheduleDao.create(scheduleArrival);
    }

    @Transactional
    public Schedule editSchedule(Schedule schedule) throws RuntimeException {
        scheduleDao.update(schedule);
        return scheduleDao.findOne(schedule.getId());
    }

    @Transactional
    public void removeSchedule(Schedule schedule) {
        scheduleDao.delete(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleDao.findAll();
    }
}
