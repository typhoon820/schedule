package com.university.schedule.DAO;

import com.university.schedule.model.Department;
import com.university.schedule.model.Subject;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SubjectDAO")
public class SubjectDAOImpl extends AbstractDAO<Integer, Subject> implements SubjectDAO {
    @Override
    public Subject findById(int id) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("id",id));
        return (Subject) criteria.uniqueResult();
    }

    @Override
    public Subject findByName(String name) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("name", name));
        return (Subject) criteria.uniqueResult();
    }

    @Override
    public void save(Subject subject) {
        getSession().persist(subject);
    }

    @Override
    public void delete(int id) {
        Query query = getSession().createSQLQuery("DELETE FROM subject WHERE id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Subject> findAll() {
        Criteria criteria = createEntityCreteria();
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

}
