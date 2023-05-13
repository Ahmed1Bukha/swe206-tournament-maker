package com.SWE.project.Classes;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

import java.util.Objects;

// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Team.class, name = "Team"),
        @JsonSubTypes.Type(value = Student.class, name = "Student")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@DiscriminatorColumn(name = "DISCRIM", discriminatorType = DiscriminatorType.STRING)
public abstract class Participant {
    @Id
    @GeneratedValue
    protected Long id;

    @Column
    protected Integer goalsMade = 0;

    @Column
    protected Integer goalsRecieved = 0;

    @Column
    protected Integer wins = 0;

    @Column
    protected Integer points = 0;

    @Column
    protected String name;

    // @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "participants", targetEntity = com.SWE.project.Classes.Tournament.class)
    @JsonIgnoreProperties({ "type", "startDate", "endDate", "timeBetweenStages", "tournamentType", "participants",
            "currentMatch", "open", "finished", "tournamentMatches", "allRounds", "currentPlayers",
            "remainingMatchesInRound" })
    protected Set<Tournament> tournaments = new HashSet<Tournament>();

    // @Transient
    // @OneToMany(mappedBy = "match_participants")
    // @JsonIgnoreProperties({ "match_participants", "tournament", "endDate" })
    // protected Set<Match> matches;

    public Participant() {
    }

    abstract void addTournament(Tournament tournament);

    abstract void win(int GoalsMade, int goalsRecieved);

    abstract void draw(int GoalsMade, int goalsRecieved);

    abstract void lost(int GoalsMade, int goalsRecieved);

    public String getName() {
        return this.name;
    }

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

    // public Set<Match> getMatches() {
    // return this.matches;
    // }

    // public void setMatches(Set<Match> matches) {
    // this.matches = matches;
    // }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Participant)) {
            return false;
        }
        Participant participant = (Participant) o;
        return Objects.equals(id, participant.id) && Objects.equals(goalsMade, participant.goalsMade)
                && Objects.equals(goalsRecieved, participant.goalsRecieved) && Objects.equals(wins, participant.wins)
                && Objects.equals(points, participant.points) && Objects.equals(name, participant.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, goalsMade, goalsRecieved, wins, points, name);
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + getId() + "'" +
                ", goalsMade='" + getGoalsMade() + "'" +
                ", goalsRecieved='" + getGoalsRecieved() + "'" +
                ", wins='" + getWins() + "'" +
                ", points='" + getPoints() + "'" +
                ", name='" + getName() + "'" +
                "}";
    }
}