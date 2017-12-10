package com.university.schedule.service;

import com.university.schedule.DAO.CycleDAO;
import com.university.schedule.model.Cycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CycleService")
@Transactional
public class CycleServiceImpl implements CycleService {
    @Autowired
    CycleDAO cycleDAO;

    @Override
    public Cycle findById(int id) {
        return cycleDAO.findById(id);
    }

    @Override
    public Cycle findByName(String name) {
        return cycleDAO.findByName(name);
    }

    @Override
    public void save(Cycle cycle) {
        cycleDAO.save(cycle);
    }

    @Override
    public void delete(int id) {
        cycleDAO.delete(id);
    }

    @Override
    public List<Cycle> findAll() {
        return cycleDAO.findAll();
    }
}
