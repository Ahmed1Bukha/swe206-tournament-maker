package com.SWE.project.Classes;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.*;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = Student.class)
@DiscriminatorValue("Student")
@JsonTypeName("Student")
public class Student extends Participant {
    @ManyToMany(mappedBy = "team_members")
    @JsonIgnoreProperties({ "team_members" })
    private Set<Team> teams = new HashSet<Team>(); // Done

    @Column
    public String email;
    @Column
    public Long studentId;

    public Student() {
    }

    public Student(Long studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public void win(int goalsMade, int goalsRecieved) {
        points += 3;
        this.goalsMade += goalsMade;
        this.goalsRecieved += goalsRecieved;
        wins++;
    }

    @Override
    public void lost(int goalsMade, int goalsRecieved) {
        this.goalsMade += goalsMade;
        this.goalsRecieved += goalsRecieved;
    }

    @Override
    public void draw(int GoalsMade, int goalsRecieved) {
        this.goalsMade += goalsMade;
        this.goalsRecieved += goalsRecieved;
        points += 1;
    }

    @Override
    public void addTournament(Tournament tournament) {
        this.tournaments.add(tournament);
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return goalsMade == student.goalsMade && goalsRecieved == student.goalsRecieved && wins == student.wins
                && points == student.points && id == student.id && Objects.equals(name,
                        student.name);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(goalsMade, goalsRecieved, wins, points, id, name);
    }

    @Override
    public String toString() {
        return "{" + super.toString().substring(1, super.toString().length() - 1) +
        // ", studentId='" + getStudentId() + "'" +
        // "Tournaments" +
                "}";
    }
}
