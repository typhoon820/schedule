package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import com.university.schedule.model.Teacher;

import java.util.List;

public interface TeacherDAO {
    Teacher findById(int id);
    Teacher findByName(String name);
    void save(Teacher teacher);
    void delete (int id);
    List<Teacher> findAll();
    List<Teacher> findUnbusy(String mmyyyy);
    void deleteEntity(Teacher teacher);
    void update(Teacher teacher);
    List<Integer> getClasses(Teacher teacher);

}
