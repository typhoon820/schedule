package com.university.schedule.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "cycle", schema = "university")
public class Cycle {
    private int id;
    private String name;
    private Collection<Subject> subjectsById;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        Cycle cycle = (Cycle) o;

        if (id != cycle.id) return false;
        if (name != null ? !name.equals(cycle.name) : cycle.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "cycleByCycleId")
    public Collection<Subject> getSubjectsById() {
        return subjectsById;
    }

    public void setSubjectsById(Collection<Subject> subjectsById) {
        this.subjectsById = subjectsById;
    }
}
