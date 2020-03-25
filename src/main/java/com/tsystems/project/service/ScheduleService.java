package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.ScheduleDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    TrainDao trainDao;

    @Autowired
    StationDao stationDao;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public void addSchedule(Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        scheduleDao.getCurrentSession().beginTransaction();
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

        scheduleDao.getCurrentSession().getTransaction().commit();
        scheduleDao.getCurrentSession().close();
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

    public Schedule getScheduleByStationId(long id) {
        scheduleDao.getCurrentSession().beginTransaction();
        Schedule schedule = scheduleDao.findByTrainArriveId(id);
        scheduleDao.getCurrentSession().getTransaction().commit();
        scheduleDao.getCurrentSession().close();
        return schedule;
    }

    public List<ScheduleDto> getSchedulesByStationId(long id) {
        scheduleDao.getCurrentSession().beginTransaction();
        List<Schedule> schedules = scheduleDao.fyndByStationId(id);
        Type listType = null;
        List<ScheduleDto> scheduleDtos = null;

        if (schedules != null) {
        listType = new TypeToken<List<ScheduleDto>>() {}.getType();
        scheduleDtos = new ModelMapper().map(schedules, listType);
        List<Long> trainsId = new ArrayList<>();

        for (int i = 0; i < scheduleDtos.size(); i++) {
            for (int j = i + 1; j < scheduleDtos.size(); j++) {
                if (scheduleDtos.get(i).getTrain().getNumber() == scheduleDtos.get(j).getTrain().getNumber()) {
                    scheduleDtos.get(i).setDepartureTime(scheduleDtos.get(j).getDepartureTime());
                    trainsId.add(scheduleDtos.get(j).getTrain().getId());
                }
            }
        }

        Iterator<ScheduleDto> iterator = scheduleDtos.iterator();

        while (iterator.hasNext()) {
            long trainId = iterator.next().getTrain().getId();
            if (trainsId.contains(trainId)){
                   iterator.remove();
            }
        }
        }
        scheduleDao.getCurrentSession().getTransaction().commit();
        scheduleDao.getCurrentSession().close();
        return scheduleDtos;
    }
}

