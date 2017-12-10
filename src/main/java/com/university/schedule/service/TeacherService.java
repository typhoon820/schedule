package com.university.schedule.service;

import com.university.schedule.model.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher findById(int id);
    Teacher findByName(String name);
    void save(Teacher teacher);
    void delete (int id);
    List<Teacher> findAll();
    List<Teacher> findFree(String mmyyyy);
    void deleteEntity(Teacher teacher);
    void update(Teacher teacher);
}
