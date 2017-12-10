package com.university.schedule.service;

import com.university.schedule.model.Cycle;

import java.util.List;

public interface CycleService {
    Cycle findById(int id);
    Cycle findByName(String name);
    void save(Cycle cycle);
    void delete (int id);
    List<Cycle> findAll();
}
