package com.university.schedule.service;

import com.university.schedule.DAO.CycleDAO;
import com.university.schedule.DAO.ScheduleDAO;
import com.university.schedule.DAO.SubjectTeacherDAO;
import com.university.schedule.DAO.TeacherDAO;
import com.university.schedule.model.*;
import javafx.scene.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;

@Service("ScheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleDAO scheduleDAO;
    @Autowired
    TeacherDAO teacherDAO;
    @Autowired
    SubjectTeacherDAO subjectTeacherDAO;
    @Autowired
    CycleDAO cycleDAO;

    @Override
    public Schedule findById(int id) {
        return scheduleDAO.findById(id);
    }


    @Override
    public void save(Schedule schedule) {
        scheduleDAO.save(schedule);
    }

    @Override
    public void delete(int id) {
        scheduleDAO.delete(id);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleDAO.findAll();
    }

    @Override
    public List<Schedule> findByTeacher(Teacher teacher) {
        //Teacher t = teacherDAO.findByName(teacher);
        List<SubjectTeacher> st = subjectTeacherDAO.findByTeacherID(teacher);
        List<Schedule> s = findAll();
        List<Schedule> res = new ArrayList<>();
        for (Schedule sc : s) {
            for (SubjectTeacher stch : st) {
                if (sc.getSubjectTeacherBySubjectTeacherId().getId() == stch.getId())
                    res.add(sc);
            }
        }
        return res;
    }

    @Override
    public List<Schedule> findByTime(int idLesson) {
        return scheduleDAO.findByTime(idLesson);
    }

    @Override
    public Map<Cycle, Integer> countSubjects(Groups group) {
        Map<Cycle, Integer> res = new HashMap<>();
        List<Schedule> schedule = scheduleDAO.findAll().stream()
                .filter(s -> s.getGroupNumber().equals(group.getGroupNo())).collect(toList());

        for(Cycle c: cycleDAO.findAll()){
            res.put(c,0);
        }

        for (Schedule s : schedule) {
            Cycle elem = s.getSubjectTeacherBySubjectTeacherId().getSubjectBySubjectId().getCycleByCycleId();
            res.put(elem,
                    res.containsKey(elem) ? res.get(elem) + 1 : 0);
        }
        return res;
    }

    @Override
    public List<Schedule> findByGroupMonth(Groups group, String monthYear) {
        return scheduleDAO.findByGroupMonth(group, monthYear);
    }

    @Override
    public Integer saveWithId(Schedule schedule) {
        return scheduleDAO.saveWithId(schedule);
    }
}
