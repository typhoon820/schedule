package com.university.schedule.service;

import com.university.schedule.DAO.GroupDAO;
import com.university.schedule.model.Groups;
import javafx.scene.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("GroupService")
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupDAO groupDAO;

    @Override
    public List<Groups> findAll() {
        return groupDAO.findAll();
    }
}
