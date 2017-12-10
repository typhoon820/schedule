package com.university.schedule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groups", schema = "university")
public class Groups {
    private String groupNo;

    @Id
    @Column(name = "group_no", nullable = false)
    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Groups groups = (Groups) o;

        return groupNo.equals(groups.groupNo);
    }

    @Override
    public int hashCode() {
        return groupNo.hashCode();
    }

    @Override
    public String toString(){
        return this.groupNo;
    }
}
