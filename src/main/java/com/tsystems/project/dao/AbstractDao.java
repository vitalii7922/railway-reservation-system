package com.tsystems.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T extends Serializable> {

    private Class<T> clazz;

    SessionFactory sessionFactory;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findOne(long id){
        return (T) getCurrentSession().get(clazz, id);
    }

    public List findAll() {
        return  getCurrentSession().createQuery("from " + clazz.getName()).list();
    }

    public T create(T entity) {
        if (entity != null) {
            getCurrentSession().saveOrUpdate(entity);
        }
        return entity;
    }

    public T update(T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
