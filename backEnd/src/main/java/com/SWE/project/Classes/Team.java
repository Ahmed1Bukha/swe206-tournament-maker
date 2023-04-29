package com.SWE.project.Classes;

import java.util.*;

import com.SWE.project.Enums.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Team extends Participant {
    @Column(name = "team_name")
    private String name;

    @ManyToMany
    @JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> team_members;

    @Column
    private GAME_TYPE gameType;

    @ManyToOne // How does this even run?
    private Tournament tournament;

    public Team() {
    }

    public Team(String name, Set<Student> team_members, GAME_TYPE gameType, Tournament tournament) {
        this.name = name;
        this.team_members = team_members;
        this.gameType = gameType;
        this.tournament = tournament;
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

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public String toString() {
        System.out.println("T ts");
        return "{" + super.toString().substring(1, super.toString().length() - 1) +
                "name='" + getName() + "'" +
                ", team_members='" + getTeam_members() + "'" +
                ", gameType='" + getGameType() + "'" +
                ", tournament='" + getTournament() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("T e");
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
        System.out.println("T hc");
        return Objects.hash(name, gameType);
    }
}
