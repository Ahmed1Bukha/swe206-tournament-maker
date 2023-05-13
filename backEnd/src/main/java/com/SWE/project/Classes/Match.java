package com.SWE.project.Classes;

import java.sql.Date;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Objects;

@Entity
public class Match {
    @Id
    @GeneratedValue
    @Column
    public Long id;

    @OneToMany
    private Participant[] matchparticipants;

    @OneToOne
    private Tournament tournament;

    @Column
    Integer scoreA = -1;

    @Column
    Integer scoreB = -1;

    @Column
    boolean finished;

    @Column
    boolean dummyMatch; // dummy matches are used when team number are odd

    @Column
    private Date endDate = new Date(0);

    public Match() {
    }
    // public Match(String word){
    // String[] array= word.split(",");
    // if(array[2].equals("Dummy")){
    // dummyMatch=finished=true;

    // }
    // }
    public Match(Participant[] match_participants, int scoreA, int scoreB,
            Boolean finished, Boolean dummyMatch) {
        this.matchparticipants = new Participant[2];
        this.matchparticipants = match_participants;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.finished = finished;
        this.dummyMatch = dummyMatch;
    }

    public Match(Participant[] match_participants, boolean dummyMatch) {
        // this.id = id;
        // this.tournament = t;
        this.matchparticipants = new Participant[2];
        this.matchparticipants[0] = match_participants[0];
        this.matchparticipants[1] = match_participants[1];
        finished = dummyMatch;
        this.dummyMatch = dummyMatch;
    }

    public void enterResults(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        finished = true;
    }

    public Participant decideWinner() {
        if (dummyMatch)
            return matchparticipants[0];
        if (scoreA > scoreB)
            return matchparticipants[0];
        return matchparticipants[1];
    }

    public Participant decideLoser() {
        if (dummyMatch)
            return matchparticipants[1];
        if (scoreA < scoreB)
            return matchparticipants[0];
        return matchparticipants[1];
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Participant[] getMatchparticipants() {
        return this.matchparticipants;
    }

    public void setMatchparticipants(Participant[] match_participants) {
        this.matchparticipants = match_participants;
    }

    public int getScoreA() {
        return this.scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public int getScoreB() {
        return this.scoreB;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament t) {
        this.tournament = t;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getDummyMatch() {
        return this.dummyMatch;
    }

    public void setDummyMatch(Boolean dummyMatch) {
        this.dummyMatch = dummyMatch;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String stringMatch() {

        if (dummyMatch) {
            return (matchparticipants[0].getId() + "," + scoreA + "," + "Dummy" + "," + "-1");

        }
        return (matchparticipants[0].getId() + "," + scoreA + "," + matchparticipants[1].getId() + "," + scoreB);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Match) {
            Match other = (Match) obj;
            if ((matchparticipants[0] == other.matchparticipants[0]
                    && matchparticipants[1] == other.matchparticipants[1])
                    || (matchparticipants[1] == other.matchparticipants[0]
                            && matchparticipants[0] == other.matchparticipants[1]))
                return true;
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        if (dummyMatch)
            return matchparticipants[0].getName();
        return matchparticipants[0].getName() + " vs " +
                matchparticipants[1].getName() + (finished ? " (" + scoreA + " - " + scoreB
                        + ")" : "");
    }

    public boolean contains(Match other) {
        if (other.matchparticipants[0] == matchparticipants[0] ||
                other.matchparticipants[1] == matchparticipants[1]
                || other.matchparticipants[1] == matchparticipants[0]
                || other.matchparticipants[0] == matchparticipants[1])
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scoreA, scoreB, finished, dummyMatch, endDate);
    }
}
