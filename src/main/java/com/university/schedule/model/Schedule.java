package com.university.schedule.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.sql.Timestamp;
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findTeacherSchedule",
                query = "call find_teacher_schedule(:idTeacher)",
                resultClass = Schedule.class
        ),
        @NamedNativeQuery(
                name = "findSchedule",
                query = "call find_schedule(:groupId, :monthYear)",
                resultClass = Schedule.class
        )
})
@Entity
@Table(name = "schedule", schema = "university")
public class Schedule {
    private int id;
    private String groupNumber;
    private Timestamp startTime;
    private Integer room;
    private SubjectTeacher subjectTeacherBySubjectTeacherId;
    private Integer timetableId;


    @Basic
    @Column(name = "timetable_id")
    public Integer getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(Integer timetableId) {
        this.timetableId = timetableId;
    }

    private String subj;
    private String tchr;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_number", nullable = false, length = 45)
    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public Timestamp getStartTime() {

        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "room", nullable = true)
    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (id != schedule.id) return false;
        if (groupNumber != null ? !groupNumber.equals(schedule.groupNumber) : schedule.groupNumber != null)
            return false;
        if (startTime != null ? !startTime.equals(schedule.startTime) : schedule.startTime != null) return false;
        if (room != null ? !room.equals(schedule.room) : schedule.room != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupNumber != null ? groupNumber.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "subject_teacherID", referencedColumnName = "ID", nullable = false)
    public SubjectTeacher getSubjectTeacherBySubjectTeacherId() {
        return subjectTeacherBySubjectTeacherId;
    }

    public void setSubjectTeacherBySubjectTeacherId(SubjectTeacher subjectTeacherBySubjectTeacherId) {
        this.subjectTeacherBySubjectTeacherId = subjectTeacherBySubjectTeacherId;
    }

    @Transient
    public StringProperty subjProperty(){
        return new SimpleStringProperty(subjectTeacherBySubjectTeacherId.getSubjectBySubjectId().getName());
    }

    @Transient
    public StringProperty tchrProperty(){
        return new SimpleStringProperty(subjectTeacherBySubjectTeacherId.getTeacherByTeacherId().getFio());
    }
    @Transient
    public StringProperty startTimeProperty(){
        return new SimpleStringProperty(startTime.toString());
    }
}
