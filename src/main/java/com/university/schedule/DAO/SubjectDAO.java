package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import com.university.schedule.model.Subject;

import java.util.List;

public interface SubjectDAO {
    Subject findById(int id);
    Subject findByName(String name);
    void save(Subject subject);
    void delete (int id);
    List<Subject> findAll();
}
