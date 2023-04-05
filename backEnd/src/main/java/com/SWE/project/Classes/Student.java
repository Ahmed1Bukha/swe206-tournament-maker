package com.SWE.project.Classes;

import java.util.Objects;
import java.util.Set;

import com.SWE.project.Classes.Tournament.RoundRobinTournament;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Students")
public class Student implements Participent {
    @Column
    private @Id long id;

    @Column
    private String name;

    @Column
    private double gpa;

    @OneToMany
    private Set<Tournament> Tournaments;

    public Student() {

    }

    public Student(long id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
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

    public double getGpa() {
        return this.gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
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
        return id == student.id && Objects.equals(name, student.name) && gpa == student.gpa
                && Objects.equals(Tournaments, student.Tournaments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gpa, Tournaments);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", gpa='" + getGpa() + "'" +
                ", Tournaments='" + getTournaments() + "'" +
                "}";
    }

}
