package com.university.schedule.service;

import com.university.schedule.DAO.CycleDAO;
import com.university.schedule.DAO.ScheduleDAO;
import com.university.schedule.DAO.SubjectTeacherDAO;
import com.university.schedule.DAO.TeacherDAO;
import com.university.schedule.model.*;
import javafx.util.converter.TimeStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("TeacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherDAO teacherDAO;

    @Autowired
    ScheduleDAO scheduleDAO;

    @Autowired
    SubjectTeacherDAO subjectTeacherDAO;

    @Override
    public Teacher findById(int id) {
        return teacherDAO.findById(id);
    }

    @Override
    public Teacher findByName(String name) {
        return teacherDAO.findByName(name);
    }

    @Override
    public void save(Teacher teacher) {
        teacherDAO.save(teacher);
    }


    @Override
    public void delete(int id) {
        teacherDAO.delete(id);
    }

    @Override
    public List<Teacher> findAll() {

        return teacherDAO.findAll();
    }

    @Override
    public List<Teacher> findFree(String mmyyyy) {
        return teacherDAO.findUnbusy(mmyyyy);
    }

    private void predelete(Teacher teacher){
        List<Schedule> schedule = scheduleDAO.findByTeacher(teacher);

        List<SubjectTeacher> st = subjectTeacherDAO.findAll();

        List<Timestamp> times = schedule.stream().map(Schedule::getStartTime).collect(Collectors.toList());

        List<Teacher> teachers = teacherDAO.findAll();
        teachers.remove(teacher);
        for (Schedule s : schedule) {
            boolean found = false;
            for (Teacher t : teachers) {
                if(times.isEmpty()){
                    break;
                }
                List<Schedule> teacherSchedule = scheduleDAO.findByTeacher(t);
                if (teacherSchedule.size() != 0) {
                    for (Schedule ts : teacherSchedule) {
                        if (!times.contains(ts.getStartTime())
                                && !found
                                && teacher.getDepartmentByDepartmentId().equals(t.getDepartmentByDepartmentId())) {
                            s.getSubjectTeacherBySubjectTeacherId().setTeacherByTeacherId(t);
                            found = true;
                            times.remove(s.getStartTime());
                            teacherDAO.update(t);
                            break;
                        }
                    }

                } else {
                    if (teacher.getDepartmentByDepartmentId().equals(t.getDepartmentByDepartmentId())) {
                        //s.getSubjectTeacherBySubjectTeacherId().setTeacherByTeacherId(t);
                        t.getSubjectTeachersById().add(s.getSubjectTeacherBySubjectTeacherId());
                        subjectTeacherDAO.updateSql(t.getId(),s.getSubjectTeacherBySubjectTeacherId().getSubjectBySubjectId().getId());
                        //s.getSubjectTeacherBySubjectTeacherId().
//                        SubjectTeacher toUpdate = s.getSubjectTeacherBySubjectTeacherId();
//                        toUpdate.setTeacherByTeacherId(t);
                        //subjectTeacherDAO.update(toUpdate);
                        found = true;
                        times.remove(s.getStartTime());
                        teacherDAO.update(t);
                        break;
                    }
                }

            }

        }
    }

    @Override
    public void deleteEntity(Teacher teacher) {
        //predelete(teacher);
        teacherDAO.deleteEntity(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        teacherDAO.update(teacher);
    }
}
