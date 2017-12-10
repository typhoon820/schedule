package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CycleDAO")
public class CycleDAOImpl extends AbstractDAO<Integer, Cycle> implements CycleDAO{

    @Override
    public Cycle findById(int id) {
        Criteria criteria = getSession().createCriteria(Cycle.class);
        criteria.add(Restrictions.eq("id",id));
        return (Cycle)criteria.uniqueResult();
    }

    @Override
    public Cycle findByName(String name) {
        Criteria criteria = getSession().createCriteria(Cycle.class);
        criteria.add(Restrictions.eq("name", name));
        return (Cycle)criteria.uniqueResult();
    }

    @Override
    public void save(Cycle cycle) {
        getSession().persist(cycle);
    }

    @Override
    public void delete(int id) {
        Query query = getSession().createSQLQuery("DELETE FROM cycle WHERE id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Cycle> findAll() {
        Criteria criteria = getSession().createCriteria(Cycle.class);
        return criteria.list();
    }
}
