package com.SWE.project.Classes;

import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// @Table(name = "Participants")
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

    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToMany(mappedBy = "participants")
    protected Set<Tournament> tournaments; // Done

    @OneToMany(mappedBy = "match_participants")
    protected Set<Match> matches;

    public Participant() {
    }

    abstract void win(int GoalsMade, int goalsRecieved);

    abstract void draw(int GoalsMade, int goalsRecieved);

    abstract void lost(int GoalsMade, int goalsRecieved);

    abstract String getName();

    abstract int getPoints();

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

}