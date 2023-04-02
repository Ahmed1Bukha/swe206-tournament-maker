package com.SWE.project;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Table(name = "Teams")
public class Team {
    @Column
    private String name;
    @Column
    private Set<Student> members;
    @Column
    private GAME_TYPE gameType;

    public Team() {
    }

    public Team(String name, Set<Student> members, GAME_TYPE gameType) {
        this.name = name;
        this.members = members;
        this.gameType = gameType;
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
        return this.members;
    }

    public void setMembers(Set<Student> members) {
        this.members = members;
    }

}

enum GAME_TYPE {
    RoundRobin, Elimination
}
