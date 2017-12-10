package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CycleDAO{
    Cycle findById(int id);
    Cycle findByName(String name);
    void save(Cycle cycle);
    void delete (int id);
    List<Cycle> findAll();
}
