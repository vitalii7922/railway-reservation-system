package com.tsystems.project.dao;

import com.tsystems.project.domain.Schedule;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ScheduleDao extends AbstractDao<Schedule> {

    public ScheduleDao() {
        super(Schedule.class);
    }

    public Schedule findByTrainId(long id) {
//        getCurrentSession().beginTransaction();
        String queryString = "SELECT s FROM Schedule s WHERE (s.train.id) = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        List<Schedule> schedules = query.getResultList();
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
        if (schedules.isEmpty()) {
            return null;
        } else {
            return schedules.get(schedules.size() - 1);
        }
    }

    public Schedule findByTrainDepartureId(long id) {
//        getCurrentSession().beginTransaction();
        String queryString = "SELECT s FROM Schedule s WHERE (s.train.id) = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        List<Schedule> schedules = query.getResultList();
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
        if (schedules.isEmpty()) {
            return null;
        } else {
            return schedules.get(0);
        }
    }


    public Schedule findByTrainArriveId(long id) {
//        getCurrentSession().beginTransaction();
        String queryString = "SELECT s FROM Schedule s WHERE (s.train.id) = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        List<Schedule> schedules = query.getResultList();
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
        if (schedules.isEmpty()) {
            return null;
        } else {
            return schedules.get(1);
        }
    }

    public List<Schedule> fyndByStationId(long id) {
        String queryString = "SELECT s FROM Schedule s WHERE (s.station.id) = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        List<Schedule> schedules = query.getResultList();
        if (schedules.isEmpty()) {
            return null;
        } else {
            return schedules;
        }
    }
}
