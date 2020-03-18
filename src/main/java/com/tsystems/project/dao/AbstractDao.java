package com.tsystems.project.dao;

import com.tsystems.project.domain.Station;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T extends Serializable> {
    private Class<T> clazz;

    @Autowired
    SessionFactory sessionFactory;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findOne(long id){
        return (T) getCurrentSession().get(clazz, id);
    }

    public List findAll() {
        getCurrentSession().beginTransaction();
        List elements = getCurrentSession().createQuery("from " + clazz.getName()).list();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return  elements;
    }


    public T create(T entity) {
        if (entity != null) {
           getCurrentSession().beginTransaction();
           getCurrentSession().saveOrUpdate(entity);
           getCurrentSession().getTransaction().commit();
           getCurrentSession().close();
        }
        return entity;
    }

    public T update(T entity) {
        getCurrentSession().beginTransaction();
        T e = (T) getCurrentSession().merge(entity);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return e;
    }

    public void delete(T entity) {
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(entity);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void deleteById(long entityId) {
        getCurrentSession().beginTransaction();
        T entity = findOne(entityId);
        delete(entity);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }



    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
