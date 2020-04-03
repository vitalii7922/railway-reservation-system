package com.tsystems.project.dao;

import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Configuration
public abstract class AbstractDao<T extends Serializable> {

    private Class<T> clazz;

    @PersistenceContext(unitName = "hibernateSpring")
    EntityManager entityManager;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findOne(long id) {
        T e = (T) entityManager.find(clazz, id);
        return e;
    }

    public List findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }


    public T create(T entity) {
        if (entity != null) {
            entityManager.persist(entity);
        }
        return entity;
    }

    public T update(T entity) {
        T e = entityManager.merge(entity);
        return e;
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }
}
