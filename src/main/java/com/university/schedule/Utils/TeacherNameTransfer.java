package com.university.schedule.Utils;

import com.university.schedule.model.Teacher;

public class TeacherNameTransfer {
    public static TeacherNameTransfer teacher = null;
    private Teacher tchr;

    private TeacherNameTransfer(){
    }

    public static TeacherNameTransfer getInstance(){
        if (teacher == null){
            teacher = new TeacherNameTransfer();
        }
        return teacher;
    }

    public Teacher getTchr() {
        return tchr;
    }

    public void setTchr(Teacher tchr) {
        this.tchr = tchr;
    }
}
