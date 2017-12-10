package com.university.schedule.service;

import com.university.schedule.model.Subject;
import com.university.schedule.model.SubjectTeacher;
import com.university.schedule.model.Teacher;

import java.util.List;

public interface SubjectTeacherService {
    SubjectTeacher findById(int id);
    void save(SubjectTeacher st);
    void delete (int id);
    List<SubjectTeacher> findAll();
    List<SubjectTeacher> findByTeacherID(Teacher t);
    List<SubjectTeacher> findBySubjectID(Subject s);
}
