package com.university.schedule.DAO;

import com.university.schedule.model.Department;
import com.university.schedule.model.Subject;
import com.university.schedule.model.SubjectTeacher;
import com.university.schedule.model.Teacher;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SubjectTeacherDAO")
public class SubjectTeacherDAOImpl extends AbstractDAO<Integer, SubjectTeacher> implements SubjectTeacherDAO {
    @Override
    public SubjectTeacher findById(int id) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("id",id));
        return (SubjectTeacher) criteria.uniqueResult();
    }


    @Override
    public void save(SubjectTeacher subjectTeacher) {
        getSession().persist(subjectTeacher);
    }

    @Override
    public void delete(int id) {
        Query query = getSession().createSQLQuery("DELETE FROM subject_teacher WHERE id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public List<SubjectTeacher> findAll() {
        Criteria criteria = createEntityCreteria();
        return criteria.list();
    }
    @Override
    public List<SubjectTeacher> findByTeacherID(Teacher t){
        Criteria criteria = getSession().createCriteria(SubjectTeacher.class);
        criteria.add(Restrictions.eq("teacherByTeacherId", t));
        return (List<SubjectTeacher>)criteria.list();
    }

    @Override
    public void update(SubjectTeacher subjectTeacher) {
        getSession().update(subjectTeacher);
    }

    @Override
    public void updateSql(int teacherId, int subjectId) {
        Query query = getSession().createSQLQuery("UPDATE subject_teacher " +
                "SET TEACHER_ID= :teacherId WHERE SUBJECT_ID = :subjectId");
        query.setInteger("teacherId", teacherId);
        query.setInteger("subjectId", subjectId);
        query.executeUpdate();
    }

    @Override
    public List<SubjectTeacher> findBySubjectID(Subject s) {
        Criteria criteria = getSession().createCriteria(SubjectTeacher.class);
        criteria.add(Restrictions.eq("subjectBySubjectId", s));
        return (List<SubjectTeacher>)criteria.list();
    }
}
