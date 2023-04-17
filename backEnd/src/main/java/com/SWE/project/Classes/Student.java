package com.SWE.project.Classes;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Students")
public class Student implements Participant {
    int goalsMade = 0;
    int goalsRecieved = 0;
    int wins = 0;
    int points = 0;
    @Column
    private @Id long id;

    @Column
    private String name;

    @OneToMany
    private Set<Tournament> Tournaments;

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
        return goalsMade == student.goalsMade && goalsRecieved == student.goalsRecieved && wins == student.wins
                && points == student.points && id == student.id && Objects.equals(name, student.name)
                && Objects.equals(Tournaments, student.Tournaments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goalsMade, goalsRecieved, wins, points, id, name, Tournaments);
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
