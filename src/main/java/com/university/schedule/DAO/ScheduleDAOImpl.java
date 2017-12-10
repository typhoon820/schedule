package com.university.schedule.DAO;

import com.university.schedule.model.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 */
@Repository("ScheduleDAO")
public class ScheduleDAOImpl extends AbstractDAO<Integer, Schedule> implements ScheduleDAO {
    @Override
    public Schedule findById(int id) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("id",id));
        return (Schedule) criteria.uniqueResult();
    }


    @Override
    public void save(Schedule schedule) {
        getSession().persist(schedule);
    }

    @Override
    public void delete(int id) {
        Query query = getSession().createSQLQuery("DELETE FROM schedule WHERE id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Schedule> findAll() {
        Criteria criteria = getSession().createCriteria(Schedule.class);
        return (List<Schedule>)criteria.list();
    }

    @Override
    public List<Schedule> findByTeacher(Teacher teacher){
        List<SubjectTeacher> subjects = (List<SubjectTeacher>) teacher.getSubjectTeachersById();
        List<Schedule> res = new ArrayList<>();
        for (SubjectTeacher s: subjects){
            res.addAll(s.getSchedulesById());
        }
        return res;
    }

    @Override
    public List<Schedule> findByTime(int idLesson) {
        Criteria criteria = getSession().createCriteria(Schedule.class);
        criteria.add(Restrictions.eq("timetableId", idLesson));
        return (List<Schedule>)criteria.list();
    }

    @Override
    public List<Schedule> findByGroupMonth(Groups group, String monthYear) {
        Query query = getSession().getNamedQuery("findSchedule")
                .setParameter("groupId", group.getGroupNo())
                .setParameter("monthYear", monthYear);
        return (List<Schedule>)query.list();
    }

    @Override
    public Integer saveWithId(Schedule schedule) {
         return (Integer) getSession().save(schedule);
    }
}
