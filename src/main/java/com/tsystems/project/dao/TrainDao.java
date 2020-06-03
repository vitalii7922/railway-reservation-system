package com.tsystems.project.dao;

import com.tsystems.project.domain.Train;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Query;

/**
 * author Vitalii Nefedov
 */
@Repository
public class TrainDao extends AbstractDao<Train> {

    String trainNumber = "number";

    public TrainDao() {
        super(Train.class);
    }

    /**
     * find train by station departure name
     *
     * @param trainNumber train number
     * @param name        station name
     * @return train model
     */
    public Train findByDepartureStation(int trainNumber, String name) {
        String queryString = "SELECT t FROM Train t WHERE (t.originStation.name) = :name and t.number = :trainNumber";
        return getTrain(trainNumber, name, queryString);
    }

    /**
     * find a train by a station name
     *
     * @param trainNumber train number
     * @param name        station name
     * @param queryString query
     * @return train
     */
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

    /**
     * find a train by a station arrival name and a train number
     *
     * @param trainNumber train number
     * @param name        station name
     * @return train
     */
    public Train findByArrivalStation(int trainNumber, String name) {
        String queryString = "SELECT t FROM Train t WHERE (t.destinationStation.name) = :name and t.number = :trainNumber";
        return getTrain(trainNumber, name, queryString);
    }

    /**
     * @param number train number
     * @return list of trains
     */
    public List<Train> findTrainListByNumber(int number) {
        String queryString = "SELECT t FROM Train t WHERE (t.number) = :number";
        Query query = entityManager.createQuery(queryString);
        query.setParameter(trainNumber, number);
        return query.getResultList();
    }

    /**
     * @param number train number
     * @return train model
     */
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

    /**
     * @param fromId        station departure id
     * @param toId          station arrival id
     * @param departureTime departure time
     * @param arrivalTime   arrival time
     * @return list of trains
     */
    public List<Train> findByStationIdAtGivenTerm(long fromId, long toId, LocalDateTime departureTime,
                                                  LocalDateTime arrivalTime) {
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

    /**
     * @param trainNumber      train number
     * @param trainDepartureId train departure id
     * @param trainArrivalId   train arrival id
     * @return list of trains
     */
    public List<Train> findTrainListByTrainDepartureAndArrivalId(int trainNumber, long trainDepartureId,
                                                                 long trainArrivalId) {
        String queryString = "select t from Train t where t.number = :trainNumber and t.id >= :trainDepartureId " +
                "and t.id <= :trainArrivalId";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("trainDepartureId", trainDepartureId);
        query.setParameter("trainArrivalId", trainArrivalId);
        query.setParameter("trainNumber", trainNumber);
        return query.getResultList();
    }

    /**
     * @param number train number
     * @return list of trains
     */
    public List<Train> findAllByNumber(int number) {
        String queryString = "select t from Train t where t.number = :number";
        Query query = entityManager.createQuery(queryString);
        query.setParameter(trainNumber, number);
        return query.getResultList();
    }
}
