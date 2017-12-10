package com.university.schedule.DAO;

import com.university.schedule.model.Groups;

import java.util.List;

public interface GroupDAO {
    List<Groups> findAll();
}
