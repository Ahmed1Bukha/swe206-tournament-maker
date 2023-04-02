package com.SWE.project.Classes;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Students")
public class Student {
    @Column(name = "student_id")
    private @Id long id;

    @Column(name = "student_name")
    private String name;

    @ManyToMany(mappedBy = "ParticipantingStudents")
    private Set<Tournament> Tournaments;

    @ManyToMany(mappedBy = "team_members")
    private Set<Team> Teams;

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tournament> getTournaments() {
        return this.Tournaments;
    }

    public void setTournaments(Set<Tournament> Tournaments) {
        this.Tournaments = Tournaments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name)
                && Objects.equals(Tournaments, student.Tournaments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, Tournaments);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", Tournaments='" + getTournaments() + "'" +
                "}";
    }

}
