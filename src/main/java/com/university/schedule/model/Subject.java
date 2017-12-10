package com.university.schedule.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "subject", schema = "university")
public class Subject {
    private int id;
    private String name;
    private Cycle cycleByCycleId;
    private boolean exam;
    private Collection<SubjectTeacher> subjectTeachersById;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name ="exam", nullable = false)
    public boolean isExam() {
        return exam;
    }

    public void setExam(boolean exam) {
        this.exam = exam;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (id != subject.id) return false;
        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "cycleID", referencedColumnName = "ID", nullable = false)
    public Cycle getCycleByCycleId() {
        return cycleByCycleId;
    }

    public void setCycleByCycleId(Cycle cycleByCycleId) {
        this.cycleByCycleId = cycleByCycleId;
    }

    @OneToMany(mappedBy = "subjectBySubjectId")
    public Collection<SubjectTeacher> getSubjectTeachersById() {
        return subjectTeachersById;
    }

    public void setSubjectTeachersById(Collection<SubjectTeacher> subjectTeachersById) {
        this.subjectTeachersById = subjectTeachersById;
    }

    @Override
    public String toString(){
        return name;
    }

    @Transient
    private BooleanProperty chosen = new SimpleBooleanProperty();

    @Transient
    public StringProperty examProperty(){
        return new SimpleStringProperty(exam ? "Да": "Нет");
    }

    @Transient
    public BooleanProperty chosenProperty(){
        return chosen;
    }

    @Transient
    public void setChosen(BooleanProperty chosen) {
        this.chosen = chosen;
    }
}
