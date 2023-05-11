package com.SWE.project.Classes;

import java.util.*;

import com.SWE.project.Enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.*;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = Team.class)
@DiscriminatorValue("Team")
@JsonTypeName("Team")
public class Team extends Participant {
    @Column(name = "team_name")
    private String name;

    @ManyToMany
    @JsonIgnoreProperties({ "type", "goalsMade", "goalsRecieved", "wins", "points", "tournaments", "matches", "teams" })
    @JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> team_members;

    @Column
    private GAME_TYPE gameType;

    public Team() {
    }

    public Team(String name, Set<Student> team_members, GAME_TYPE gameType, Tournament tournament) {
        this.name = name;
        this.team_members = team_members;
        this.gameType = gameType;
        this.tournaments = new HashSet<Tournament>(Arrays.asList(tournament));
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
        this.tournaments = new HashSet<Tournament>(Arrays.asList(tournament));
        for (Student i : team_members) {
            i.addTournament(tournament);
        }
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GAME_TYPE getGameType() {
        return this.gameType;
    }

    public void setGameType(GAME_TYPE gameType) {
        this.gameType = gameType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getTeam_members() {
        return this.team_members;
    }

    public void setTeam_members(Set<Student> members) {
        this.team_members = members;
    }

    public Set<Tournament> getTournaments() {
        return this.tournaments;
    }

    public void setTournaments(Tournament tournament) {
        this.tournaments = new HashSet<Tournament>(Arrays.asList(tournament));
    }

    @Override
    public String toString() {
        return "{" + super.toString().substring(1, super.toString().length() - 1) +
                ", name='" + getName() + "'" +
                ", team_members='" + getTeam_members() + "'" +
                ", gameType='" + getGameType() + "'" +
                ", tournament='" + getTournaments() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Team)) {
            return false;
        }
        Team team = (Team) o;
        return Objects.equals(name, team.name)
                && Objects.equals(gameType, team.gameType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gameType);
    }
}
