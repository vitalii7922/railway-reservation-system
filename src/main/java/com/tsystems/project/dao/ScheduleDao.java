package com.tsystems.project.dao;

import com.tsystems.project.domain.Schedule;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * author Vitalii Nefedov
 */
@Repository
public class ScheduleDao extends AbstractDao<Schedule> {

    public ScheduleDao() {
        super(Schedule.class);
    }

    /**
     * @param id train identification
     * @return schedule model
     */
    public Schedule findByTrainId(long id) {
        String queryString = "SELECT s FROM Schedule s WHERE (s.train.id) = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        List<Schedule> schedules = query.getResultList();
        if (schedules.isEmpty()) {
            return null;
        } else {
            return schedules.get(1);
        }
    }


    /**
     * find list of schedules by station id
     *
     * @param id station identification
     * @return list of schedules
     */
    public List<Schedule> findByStationId(long id) {
        String queryString = "SELECT s FROM Schedule s WHERE (s.station.id) = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
