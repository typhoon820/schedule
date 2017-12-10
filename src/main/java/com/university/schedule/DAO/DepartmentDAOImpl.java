package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import com.university.schedule.model.Department;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("DepartmentDAO")
public class DepartmentDAOImpl extends AbstractDAO<Integer, Department> implements DepartmentDAO {
    @Override
    public Department findById(int id) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("id",id));
        return (Department) criteria.uniqueResult();
    }

    @Override
    public Department findByName(String name) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("name", name));
        return (Department) criteria.uniqueResult();
    }

    @Override
    public void save(Department department) {
        getSession().persist(department);
    }

    @Override
    public void delete(int id) {
        Query query = getSession().createSQLQuery("DELETE FROM department WHERE id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Department> findAll() {
        Criteria criteria = createEntityCreteria();
        return criteria.list();
    }
}