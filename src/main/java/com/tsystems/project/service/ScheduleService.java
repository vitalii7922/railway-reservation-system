package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleDao scheduleDao;

    @Transactional
    public Schedule addSchedule(Schedule schedule) {
        scheduleDao.create(schedule);
        return schedule;
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
