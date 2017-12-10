package com.university.schedule.service;

import com.university.schedule.DAO.TimetableDAO;
import com.university.schedule.model.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TimetableService")
@Transactional
public class TimetableServiceImpl implements TimetableService{

    @Autowired
    TimetableDAO timetableDAO;

    @Override
    public List<Timetable> findAll() {
        return timetableDAO.findAll();
    }
}
