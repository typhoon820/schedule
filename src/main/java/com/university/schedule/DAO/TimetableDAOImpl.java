package com.university.schedule.DAO;

import com.university.schedule.model.Timetable;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository("TimetableDAO")
public class TimetableDAOImpl extends AbstractDAO<Integer, Timetable> implements TimetableDAO{
    @Override
    public List<Timetable> findAll() {
        Criteria criteria = getSession().createCriteria(Timetable.class);
        return (List<Timetable>)criteria.list();
    }
}
