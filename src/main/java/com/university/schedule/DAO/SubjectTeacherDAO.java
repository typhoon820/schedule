package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import com.university.schedule.model.Subject;
import com.university.schedule.model.SubjectTeacher;
import com.university.schedule.model.Teacher;

import java.util.List;

public interface SubjectTeacherDAO {
    SubjectTeacher findById(int id);
    void save(SubjectTeacher st);
    void delete (int id);
    List<SubjectTeacher> findAll();
    List<SubjectTeacher> findByTeacherID(Teacher t);
    void update (SubjectTeacher subjectTeacher);
    void updateSql(int teacherId, int subjectId);
    List<SubjectTeacher> findBySubjectID(Subject s);
}
