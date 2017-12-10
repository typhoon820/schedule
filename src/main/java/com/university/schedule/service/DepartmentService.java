package com.university.schedule.service;

import com.university.schedule.model.Department;

import java.util.List;

public interface DepartmentService {
    Department findById(int id);
    Department findByName(String name);
    void save(Department department);
    void delete (int id);
    List<Department> findAll();
}
