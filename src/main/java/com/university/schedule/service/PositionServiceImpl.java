package com.university.schedule.service;

import com.university.schedule.DAO.CycleDAO;
import com.university.schedule.DAO.PositionDAO;
import com.university.schedule.model.Cycle;
import com.university.schedule.model.Position;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("PositionService")
@Transactional
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionDAO positionDAO;

    @Override
    public Position findById(int id) {
        return positionDAO.findById(id);
    }

    @Override
    public Position findByName(String name) {
        return positionDAO.findByName(name);
    }

    @Override
    public void save(Position position) {
        positionDAO.save(position);
    }

    @Override
    public void delete(int id) {
        positionDAO.delete(id);
    }

    @Override
    public List<Position> findAll() {
        return positionDAO.findAll();
    }
}
