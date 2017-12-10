package com.university.schedule.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Collection;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findUnbusyTeachers",
                query = "call find_unbusy_teacher(:mmyyyy)",
                resultClass = Teacher.class
        )
})

@Entity
@Table(name = "teacher", schema = "university")
public class Teacher {
    private int id;
    private String fio;
    private String adress;
    private String scientificDegree;
    private String phone;
    private Collection<SubjectTeacher> subjectTeachersById;
    private Position positionByPositionId;
    private Department departmentByDepartmentId;

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
    @Column(name = "FIO", nullable = false, length = 128)
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Basic
    @Column(name = "adress", nullable = true, length = 256)
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Basic
    @Column(name = "scientific_degree", nullable = false, length = 45)
    public String getScientificDegree() {
        return scientificDegree;
    }

    public void setScientificDegree(String scientificDegree) {
        this.scientificDegree = scientificDegree;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 10)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (id != teacher.id) return false;
        if (fio != null ? !fio.equals(teacher.fio) : teacher.fio != null) return false;
        if (adress != null ? !adress.equals(teacher.adress) : teacher.adress != null) return false;
        if (scientificDegree != null ? !scientificDegree.equals(teacher.scientificDegree) : teacher.scientificDegree != null)
            return false;
        if (phone != null ? !phone.equals(teacher.phone) : teacher.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        result = 31 * result + (scientificDegree != null ? scientificDegree.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "teacherByTeacherId", fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    public Collection<SubjectTeacher> getSubjectTeachersById() {
        return subjectTeachersById;
    }

    public void setSubjectTeachersById(Collection<SubjectTeacher> subjectTeachersById) {
        this.subjectTeachersById = subjectTeachersById;
    }

    @ManyToOne
    @JoinColumn(name = "positionID", referencedColumnName = "ID", nullable = false)
    public Position getPositionByPositionId() {
        return positionByPositionId;
    }

    public void setPositionByPositionId(Position positionByPositionId) {
        this.positionByPositionId = positionByPositionId;
    }

    @ManyToOne
    @JoinColumn(name = "departmentID", referencedColumnName = "ID")
    public Department getDepartmentByDepartmentId() {
        return departmentByDepartmentId;
    }

    public void setDepartmentByDepartmentId(Department departmentByDepartmentId) {
        this.departmentByDepartmentId = departmentByDepartmentId;
    }

    @Transient
    public StringProperty positionByPositionIdProperty(){
        return new SimpleStringProperty(positionByPositionId.getPosition());
    }

    @Transient
    public StringProperty departmentByDepartmentIdProperty(){
        return new SimpleStringProperty(departmentByDepartmentId.getName());
    }

    @Override
    public String toString(){
        return fio;
    }
}
