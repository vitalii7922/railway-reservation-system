package com.tsystems.project.dao;

import com.tsystems.project.domain.Train;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.traversal.TreeWalker;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class TrainDao extends AbstractDao<Train> {
    public TrainDao() {
        super(Train.class);
    }

    public Train findByStationId(long id) {
        getCurrentSession().beginTransaction();
        String queryString = "SELECT t FROM Train t WHERE (t.destinationStation.id) = :id";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("id", id);
        List<Train> trains = query.getResultList();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        if (trains.isEmpty()) {
            return null;
        } else {
            return trains.get(0);
        }
    }

    public Train findByNumber(int number) {
        getCurrentSession().beginTransaction();
        String queryString = "SELECT t FROM Train t WHERE (t.number) = :number";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("number", number);
        List<Train> trains = query.getResultList();
        Train train;
        if (trains.isEmpty()) {
            return null;
        } else {
            train = trains.get(trains.size() - 1);
        }
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return train;
    }
}
