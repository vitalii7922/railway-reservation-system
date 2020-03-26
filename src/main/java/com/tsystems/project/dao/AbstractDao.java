package com.tsystems.project.dao;

import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Configuration
public abstract class AbstractDao<T extends Serializable> {

    private Class<T> clazz;


//    SessionFactory sessionFactory;

    @PersistenceContext(unitName = "hibernateSpring")
    EntityManager entityManager;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T findOne(long id){
//        getCurrentSession().beginTransaction();
        T e = (T) entityManager.find(clazz, id);
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
        return  e;
    }

    public List findAll() {
//        getCurrentSession().beginTransaction();
        List elements = entityManager.createQuery("from " + clazz.getName()).getResultList();
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
        return  elements;
    }


    public T create(T entity) {
        if (entity != null) {
//           getCurrentSession().beginTransaction();
            entityManager.persist(entity);
//           getCurrentSession().saveOrUpdate(entity);
//           getCurrentSession().getTransaction().commit();
//           getCurrentSession().close();
        }
        return entity;
    }

    public T update(T entity) {
//        getCurrentSession().beginTransaction();
        T e = entityManager.merge(entity);
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
        return e;
    }

    public void delete(T entity) {
//        getCurrentSession().beginTransaction();
        entityManager.remove(entity);
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
    }

    public void deleteById(long entityId) {
//        getCurrentSession().beginTransaction();
        T entity = findOne(entityId);
        delete(entity);
//        getCurrentSession().getTransaction().commit();
//        getCurrentSession().close();
    }



  /*  public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }*/

}
