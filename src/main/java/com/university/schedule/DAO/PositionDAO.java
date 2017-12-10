package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import com.university.schedule.model.Position;
import javafx.geometry.Pos;

import java.util.List;

public interface PositionDAO {
    Position findById(int id);
    Position findByName(String position);
    void save(Position position);
    void delete (int id);
    List<Position> findAll();
}
