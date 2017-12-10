package com.university.schedule.DAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractDAO<PK extends Serializable, E> {

    private final Class<PK> persistenceClass;

    @Autowired
    SessionFactory _sessionFactory;



    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.persistenceClass = (Class<PK>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected Session getSession(){
        return _sessionFactory.getCurrentSession();
    }

    protected Session startSession(){
        return _sessionFactory.openSession();
    }

    public void persist(E entity){
        getSession().persist(entity);
    }

    public void delete(E entity){
        getSession().delete(entity);
    }

    protected Criteria createEntityCreteria(){
        return getSession().createCriteria(persistenceClass);
    }

}
