package com.university.schedule.model;


import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="timetable", schema = "university")
public class Timetable {
    private int classNumber;
    private Time startTime;

    @Basic
    @Column(name = "start_time")
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Id
    @Column(name = "class_number", nullable = false)
    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timetable timetable = (Timetable) o;

        if (classNumber != timetable.classNumber) return false;
        return startTime.equals(timetable.startTime);
    }

    @Override
    public int hashCode() {
        int result = classNumber;
        result = 31 * result + startTime.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return startTime.toString() + " "+ this.classNumber +"-я пара" ;
    }
}
