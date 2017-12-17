package com.university.schedule.service;

import com.university.schedule.model.Cycle;
import com.university.schedule.model.Groups;
import com.university.schedule.model.Schedule;
import com.university.schedule.model.Teacher;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    Schedule findById(int id);
    void save(Schedule cycle);
    void delete (int id);
    List<Schedule> findAll();
    List<Schedule> findByTeacher(Teacher teacher);
    List<Schedule> findByTime(int idLesson);
    Map<Cycle, Integer> countSubjects(Groups group);
    List<Schedule> findByGroupMonth(Groups group, String monthYear);
    Integer saveWithId(Schedule schedule);
    void update(Schedule schedule);
}
