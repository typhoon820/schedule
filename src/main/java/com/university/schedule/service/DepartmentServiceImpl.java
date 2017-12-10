package com.university.schedule.service;

import com.university.schedule.DAO.CycleDAO;
import com.university.schedule.DAO.DepartmentDAO;
import com.university.schedule.model.Cycle;
import com.university.schedule.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("DepartmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentDAO departmentDAO;

    @Override
    public Department findById(int id) {
        return departmentDAO.findById(id);
    }

    @Override
    public Department findByName(String name) {
        return departmentDAO.findByName(name);
    }

    @Override
    public void save(Department department) {
        departmentDAO.save(department);
    }

    @Override
    public void delete(int id) {
        departmentDAO.delete(id);
    }

    @Override
    public List<Department> findAll() {
        return departmentDAO.findAll();
    }
}
