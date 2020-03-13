package com.tsystems.project.dao;

import com.tsystems.project.domain.Schedule;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao extends AbstractDao<Schedule> {

    public ScheduleDao() {
        super(Schedule.class);
    }
}
