package com.tsystems.project.dao;

import com.tsystems.project.model.Train;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Query;

@Repository
public class TrainDao extends AbstractDao<Train> {

    String trainNumber = "number";

    public TrainDao() {
        super(Train.class);
    }

    public Train findByStationDeparture(int trainNumber, String name) {
        String queryString = "SELECT t FROM Train t WHERE (t.originStation.name) = :name and t.number = :trainNumber";
        return getTrain(trainNumber, name, queryString);
    }

    @Nullable
    private Train getTrain(int trainNumber, String name, String queryString) {
        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", name);
        query.setParameter("trainNumber", trainNumber);
        List<Train> trains = query.getResultList();
        if (trains.isEmpty()) {
            return null;
        } else {
            return trains.get(0);
        }
    }

    public Train findByStationArrival(int trainNumber, String name) {
        String queryString = "SELECT t FROM Train t WHERE (t.destinationStation.name) = :name and t.number = :trainNumber";
        return getTrain(trainNumber, name, queryString);
    }

    public List<Train> findTrainsByNumber(int number) {
        String queryString = "SELECT t FROM Train t WHERE (t.number) = :number";
        Query query = entityManager.createQuery(queryString);
        query.setParameter(trainNumber, number);
        return query.getResultList();
        }

    public Train findByNumber(int number) {
        String queryString = "select t from Train t inner join Station s on  s.id=t.originStation.id " +
                "where (t.number) = :number";
        Query query = entityManager.createQuery(queryString);
        query.setParameter(trainNumber, number);
        List<Train> trains = query.getResultList();
        if (trains.isEmpty()) {
            return null;
        } else {
            return trains.get(trains.size() - 1);
        }
    }

    public List<Train> findByStations(long fromId, long toId, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        String queryString1 = "select t from Train t inner join Schedule s on t.id = s.train.id " +
                "where t.originStation.id = :fromId and s.departureTime >= :departureTime";
        String queryString2 = "select t from Train t inner join Schedule s on t.id = s.train.id " +
                "where t.destinationStation.id = :toId and s.arrivalTime <= :arrivalTime";
        Query query1 = entityManager.createQuery(queryString1);
        Query query2 = entityManager.createQuery(queryString2);
        query1.setParameter("fromId", fromId);
        query2.setParameter("toId", toId);
        query1.setParameter("departureTime", departureTime);
        query2.setParameter("arrivalTime", arrivalTime);
        List<Train> trains = query1.getResultList();
        List<Train> trains2 = query2.getResultList();
        trains.addAll(trains2);
        return trains;
    }

    public List<Train> findAllTrainsBetweenTwoStations(long trainDepartureId, long trainArrivalId) {
        String queryString1 = "select t from Train t where t.id >= :trainDepartureId and t.id <= :trainArrivalId";
        Query query = entityManager.createQuery(queryString1);
        query.setParameter("trainDepartureId", trainDepartureId);
        query.setParameter("trainArrivalId", trainArrivalId);
        return query.getResultList();
    }

    public List<Train> findAllByNumber(int number) {
        String queryString = "select t from Train t where t.number = :number";
        Query query = entityManager.createQuery(queryString);
        query.setParameter(trainNumber, number);
        return query.getResultList();
    }
}
