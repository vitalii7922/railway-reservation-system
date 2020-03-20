package com.tsystems.project.dao;

import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleDao extends AbstractDao<Schedule> {

    public ScheduleDao() {
        super(Schedule.class);
    }

    public Schedule findByTrainId(long id) {
        getCurrentSession().beginTransaction();
        String queryString = "SELECT s FROM Schedule s WHERE (s.train.id) = :id";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("id", id);
        List<Schedule> schedules = query.getResultList();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        if (schedules.isEmpty()) {
            return null;
        } else {
            return schedules.get(0);
        }
    }
}
