package com.SWE.project.Classes;

import java.util.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.SWE.project.Enums.*;

import jakarta.persistence.*;

@Entity
public class Team extends Participant {
    @Column(name = "team_name")
    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToMany
    @JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> team_members; // Done

    @Column
    private GAME_TYPE gameType;

    public Team() {
    }

    public Team(int team_id, String team_name, Set<Student> team_members,
            GAME_TYPE gameType, Tournament tournament) {
        this.id = team_id;
        this.name = team_name;
        this.team_members = team_members;
        this.gameType = gameType;
        this.tournaments = new HashSet<Tournament>(Arrays.asList(tournament));
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

    public Set<Student> getMembers() {
        return this.team_members;
    }

    public void setMembers(Set<Student> members) {
        this.team_members = members;
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
    public int getPoints() {
        return points;
    }
}
