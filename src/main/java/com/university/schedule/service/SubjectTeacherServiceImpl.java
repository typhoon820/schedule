package com.university.schedule.service;

import com.university.schedule.DAO.SubjectTeacherDAO;
import com.university.schedule.model.Subject;
import com.university.schedule.model.SubjectTeacher;
import com.university.schedule.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("SubjjectTeacherService")
@Transactional
public class SubjectTeacherServiceImpl implements SubjectTeacherService {

    @Autowired
    SubjectTeacherDAO subjectTeacherDAO;

    @Override
    public SubjectTeacher findById(int id) {
        return subjectTeacherDAO.findById(id);
    }

    @Override
    public void save(SubjectTeacher st) {
        subjectTeacherDAO.save(st);
    }

    @Override
    public void delete(int id) {
        subjectTeacherDAO.delete(id);
    }

    @Override
    public List<SubjectTeacher> findAll() {
        return subjectTeacherDAO.findAll();
    }

    @Override
    public List<SubjectTeacher> findByTeacherID(Teacher t) {
        return subjectTeacherDAO.findByTeacherID(t);
    }

    @Override
    public List<SubjectTeacher> findBySubjectID(Subject s) {
        return subjectTeacherDAO.findBySubjectID(s);
    }
}
