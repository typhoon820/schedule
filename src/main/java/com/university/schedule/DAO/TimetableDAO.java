package com.university.schedule.DAO;

import com.university.schedule.model.Timetable;

import java.util.List;

public interface TimetableDAO {
    List<Timetable> findAll();
}
