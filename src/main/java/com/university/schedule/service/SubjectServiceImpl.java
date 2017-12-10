package com.university.schedule.service;

import com.sun.net.httpserver.Authenticator;
import com.university.schedule.DAO.CycleDAO;
import com.university.schedule.DAO.SubjectDAO;
import com.university.schedule.model.Cycle;
import com.university.schedule.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("SubjectService")
@Transactional
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectDAO subjectDAO;

    @Override
    public Subject findById(int id) {
        return subjectDAO.findById(id);
    }

    @Override
    public Subject findByName(String name) {
        return subjectDAO.findByName(name);
    }

    @Override
    public void save(Subject subject) {
        subjectDAO.save(subject);
    }

    @Override
    public void delete(int id) {
        subjectDAO.delete(id);
    }

    @Override
    public List<Subject> findAll() {
        return subjectDAO.findAll();
    }
}
