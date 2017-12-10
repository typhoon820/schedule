package com.university.schedule.DAO;

import com.university.schedule.model.Department;
import com.university.schedule.model.Teacher;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.internal.ProcedureCallImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Repository("TeacherDAO")
public class TeacherDAOImpl extends AbstractDAO<Integer, Teacher> implements TeacherDAO {
    @Override
    public Teacher findById(int id) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("id", id));
        return (Teacher) criteria.uniqueResult();
    }

    @Override
    public Teacher findByName(String name) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("fio", name));
        return (Teacher) criteria.uniqueResult();
    }

    @Override
    public void save(Teacher teacher) {
        getSession().persist(teacher);
    }

    @Override
    public void delete(int id) {
//        Query query = getSession().createSQLQuery("DELETE FROM teacher WHERE id = :id");
//        query.setInteger("id", id);
//        query.executeUpdate();
        getSession().delete(findById(id));
    }

    @Override
    public List<Teacher> findAll() {
        Criteria criteria = createEntityCreteria();
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    @Override
    public List<Teacher> findUnbusy(String mmyyyy) {
        Query query = getSession().getNamedQuery("findUnbusyTeachers")
                .setParameter("mmyyyy", mmyyyy);
        return (List<Teacher>) query.list();
    }

    @Override
    public void deleteEntity(Teacher teacher) {
        getSession().delete(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        getSession().update(teacher);
    }

    @Override
    public List<Integer> getClasses(Teacher teacher) {
        return null;
    }
}
