package com.university.schedule.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "position", schema = "university")
public class Position {
    private int id;
    private String position;
    private Collection<Teacher> teachersById;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "position", nullable = true, length = 45)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position1 = (Position) o;

        if (id != position1.id) return false;
        if (position != null ? !position.equals(position1.position) : position1.position != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return getPosition();
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "positionByPositionId")
    public Collection<Teacher> getTeachersById() {
        return teachersById;
    }

    public void setTeachersById(Collection<Teacher> teachersById) {
        this.teachersById = teachersById;
    }
}
