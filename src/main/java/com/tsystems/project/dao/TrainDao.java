package com.tsystems.project.dao;

import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<String> findByNumbers(int number) {
        getCurrentSession().beginTransaction();
        String queryString = "SELECT t FROM Train t WHERE (t.number) = :number";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("number", number);
        List<Train> trains = query.getResultList();
        List<String> stationNames = new ArrayList<>();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        for (int i = 0; i < trains.size(); i++) {
            stationNames.add(trains.get(i).getOriginStation().getName());
            if (i == trains.size() - 1){
                stationNames.add(trains.get(i).getDestinationStation().getName());
            }
        }

            return stationNames;
        }

    public Train findByNumber(int number) {
        getCurrentSession().beginTransaction();
        String queryString = "select t from Train t inner join Station s on  s.id=t.originStation.id where (t.number) = :number";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("number", number);
        List<Train> trains = query.getResultList();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        if (trains.isEmpty()) {
            return null;
        } else {

            return trains.get(trains.size() - 1);
        }
    }

    public List<Train> findByStations(long fromId, long toId, LocalDateTime departureTime, LocalDateTime arrivalTime) {
//        getCurrentSession().beginTransaction();
        String queryString1 = "select t from Train t where t.originStation.id = :fromId";
        String queryString2 = "select t from Train t where t.destinationStation.id = :toId";
        Query query1 = getCurrentSession().createQuery(queryString1);
        Query query2 = getCurrentSession().createQuery(queryString2);
        query1.setParameter("fromId", fromId);
        query2.setParameter("toId", toId);
        List<Train> trains = query1.getResultList();
        List<Train> trains2 = query2.getResultList();
        trains.addAll(trains2);
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
        if (trains.isEmpty()) {
            return null;
        } else {
            return trains;
        }
    }


}
