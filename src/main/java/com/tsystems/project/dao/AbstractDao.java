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
        return entityManager.find(clazz, id);
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

    public void update(T entity) {
        entityManager.merge(entity);
    }
}
