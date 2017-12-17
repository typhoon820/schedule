package com.university.schedule.DAO;

import com.university.schedule.model.Cycle;
import com.university.schedule.model.Groups;
import com.university.schedule.model.Schedule;
import com.university.schedule.model.Teacher;

import java.util.List;

public interface ScheduleDAO {
    Schedule findById(int id);
    void save(Schedule cycle);
    void delete (int id);
    List<Schedule> findAll();
    List<Schedule> findByTeacher(Teacher teacher);
    List<Schedule> findByTime(int idLesson);
    List<Schedule> findByGroupMonth(Groups group, String monthYear);
    Integer saveWithId(Schedule schedule);
    void update(Schedule schedule);
}
