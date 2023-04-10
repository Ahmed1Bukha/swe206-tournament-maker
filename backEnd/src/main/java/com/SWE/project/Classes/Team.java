package com.SWE.project.Classes;

import java.util.Set;

import com.SWE.project.Enums.GAME_TYPE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Teams")
public class Team implements Participent {
    int goalsMade=0; 
    int goalsRecieved=0;
    int points=0;
    int wins=0;
    @Column(name = "team_id")
    private @Id @GeneratedValue int id;

    @Column(name = "team_name")
    private String name;

    @ManyToMany
    @JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> team_members;

    @Column
    private GAME_TYPE gameType;

    @ManyToOne
    @JoinColumn(name = "tournament_name")
    private Tournament tournament;

    public Team(int team_id, String team_name, Set<Student> team_members, GAME_TYPE gameType, Tournament tournament) {
        this.id = team_id;
        this.name = team_name;
        this.team_members = team_members;
        this.gameType = gameType;
        this.tournament = tournament;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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
        points+=3;
        this.goalsMade+=goalsMade;
        this.goalsRecieved+= goalsRecieved;
        wins++;
    }
    @Override
    public void lost(int goalsMade, int goalsRecieved) {
        this.goalsMade+=goalsMade;
        this.goalsRecieved+= goalsRecieved;
    }
    @Override
    public void draw(int GoalsMade, int goalsRecieved) {
        this.goalsMade+=goalsMade;
        this.goalsRecieved+= goalsRecieved;
        points+=1;
        
    }
}
