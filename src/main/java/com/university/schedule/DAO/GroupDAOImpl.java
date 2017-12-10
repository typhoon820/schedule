package com.university.schedule.DAO;

import com.university.schedule.model.Groups;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("GroupDAO")
public class GroupDAOImpl extends AbstractDAO<String, Groups> implements GroupDAO{

    @Override
    public List<Groups> findAll() {
        Criteria criteria = getSession().createCriteria(Groups.class);
        return (List<Groups>)criteria.list();
    }
}
