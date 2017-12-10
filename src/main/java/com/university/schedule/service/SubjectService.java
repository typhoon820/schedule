package com.university.schedule.service;

import com.university.schedule.model.Subject;

import java.util.List;

public interface SubjectService {
    Subject findById(int id);
    Subject findByName(String name);
    void save(Subject subject);
    void delete (int id);
    List<Subject> findAll();
}
