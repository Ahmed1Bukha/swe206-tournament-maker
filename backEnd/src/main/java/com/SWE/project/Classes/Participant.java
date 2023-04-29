package com.SWE.project.Classes;

import java.util.Set;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Participant {
    @Id
    @GeneratedValue
    protected long id;

    @Column
    protected int goalsMade = 0;

    @Column
    protected int goalsRecieved = 0;

    @Column
    protected int wins = 0;

    @Column
    protected int points = 0;

    @Column
    protected String name;

    @ManyToMany(mappedBy = "participants", targetEntity = com.SWE.project.Classes.Tournament.class)
    protected Set<Tournament> tournaments;

    @OneToMany(mappedBy = "match_participants")
    protected Set<Match> matches;

    public Participant() {
    }

    abstract void addTournament(Tournament tournament);

    abstract void win(int GoalsMade, int goalsRecieved);

    abstract void draw(int GoalsMade, int goalsRecieved);

    abstract void lost(int GoalsMade, int goalsRecieved);

    abstract String getName();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Tournament> getTournaments() {
        return this.tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public int getGoalsMade() {
        return this.goalsMade;
    }

    public void setGoalsMade(int goalsMade) {
        this.goalsMade = goalsMade;
    }

    public int getGoalsRecieved() {
        return this.goalsRecieved;
    }

    public void setGoalsRecieved(int goalsRecieved) {
        this.goalsRecieved = goalsRecieved;
    }

    public int getWins() {
        return this.wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Match> getMatches() {
        return this.matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        System.out.println("P ts");
        return "{" +
                " id='" + getId() + "'" +
                ", goalsMade='" + getGoalsMade() + "'" +
                ", goalsRecieved='" + getGoalsRecieved() + "'" +
                ", wins='" + getWins() + "'" +
                ", points='" + getPoints() + "'" +
                ", name='" + getName() + "'" +
                ", matches='" + getMatches() + "'" +
                "}";
    }
}