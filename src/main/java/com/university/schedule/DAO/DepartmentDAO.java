package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import com.university.schedule.model.Department;

import java.util.List;

public interface DepartmentDAO {
    Department findById(int id);
    Department findByName(String name);
    void save(Department department);
    void delete (int id);
    List<Department> findAll();
}
