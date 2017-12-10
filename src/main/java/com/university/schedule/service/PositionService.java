package com.university.schedule.service;

import com.university.schedule.model.Position;

import java.util.List;

public interface PositionService {
    Position findById(int id);
    Position findByName(String position);
    void save(Position position);
    void delete (int id);
    List<Position> findAll();
}
