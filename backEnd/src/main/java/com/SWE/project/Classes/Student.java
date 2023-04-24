package com.SWE.project.Classes;

import java.util.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

@Entity
public class Student extends Participant {
    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToMany(mappedBy = "team_members")
    private Set<Team> teams; // Done

    public Student() {
    }

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
                        student.name)
                && Objects.equals(tournaments, student.tournaments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goalsMade, goalsRecieved, wins, points, id, name,
                tournaments);
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
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", Tournaments='" + getTournaments() + "'" +
                "}";
    }

    @Override
    public int getPoints() {
        return points;
    }
}
