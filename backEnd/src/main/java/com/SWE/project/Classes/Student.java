package com.SWE.project.Classes;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class Student extends Participant {
    @ManyToMany(mappedBy = "team_members")
    private Set<Team> teams; // Done

    @Column
    private long studentId;

    public Student() {
    }

    public Student(long studentId, String name) {
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

    public long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("S e");
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
        System.out.println("S hc");
        return Objects.hash(goalsMade, goalsRecieved, wins, points, id, name);
    }

    @Override
    public String toString() {
        System.out.println("S ts");
        return "{" + super.toString().substring(1, super.toString().length() - 1) +
                "id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                "}";
    }
}
