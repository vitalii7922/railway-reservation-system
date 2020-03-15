package com.tsystems.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.Serializable;
import java.util.List;

@EnableTransactionManagement
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
        return  getCurrentSession().createQuery("from " + clazz.getName()).list();
    }


    public T create(T entity) {
        if (entity != null) {
           getCurrentSession().beginTransaction();
           getCurrentSession().saveOrUpdate(entity);
           getCurrentSession().getTransaction().commit();
        }
        return entity;
    }

    public T update(T entity) {
        getCurrentSession().beginTransaction();
        T e = (T) getCurrentSession().merge(entity);
        getCurrentSession().getTransaction().commit();
        return e;
    }

    public void delete(T entity) {
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(entity);
        getCurrentSession().getTransaction().commit();
    }

    public void deleteById(long entityId) {
        getCurrentSession().beginTransaction();
        T entity = findOne(entityId);
        delete(entity);
        getCurrentSession().getTransaction().commit();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
