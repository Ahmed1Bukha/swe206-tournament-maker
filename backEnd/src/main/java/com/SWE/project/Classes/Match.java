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
    private Long id;

    @ManyToOne
    private Participant[] match_participants;

    @OneToOne(mappedBy = "currentMatch")
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
    private Date endDate;

    public Match() {
    }

    public Match(Participant[] match_participants, int scoreA, int scoreB,
            Boolean finished, Boolean dummyMatch, Date endDate) {
        this.match_participants = match_participants;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.finished = finished;
        this.dummyMatch = dummyMatch;
        this.endDate = endDate;
    }

    public Match(Participant[] match_participants, boolean dummyMatch) {
        // this.id = id;
        // this.tournament = t;
        this.match_participants = new Participant[2];
        this.match_participants[0] = match_participants[0];
        this.match_participants[1] = match_participants[1];
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
            return match_participants[0];
        if (scoreA > scoreB)
            return match_participants[0];
        return match_participants[1];
    }

    public Participant decideLoser() {
        if (dummyMatch)
            return match_participants[1];
        if (scoreA < scoreB)
            return match_participants[0];
        return match_participants[1];
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Participant[] getMatch_participants() {
        return this.match_participants;
    }

    public void setMatch_participants(Participant[] match_participants) {
        this.match_participants = match_participants;
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
    public HashMap<String,Integer> hashMatch(){
        HashMap<String,Integer> retMap= new HashMap<>();
        if(dummyMatch){
            retMap.put(match_participants[0].getId()+"", scoreA);
            retMap.put("Dummy", scoreB);
            return retMap;
        }
        retMap.put(match_participants[0].getId()+"", scoreA);
        retMap.put(match_participants[1].getId()+"", scoreB);
        return retMap;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Match) {
            Match other = (Match) obj;
            if ((match_participants[0] == other.match_participants[0]
                    && match_participants[1] == other.match_participants[1])
                    || (match_participants[1] == other.match_participants[0]
                            && match_participants[0] == other.match_participants[1]))
                return true;
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        if (dummyMatch)
            return match_participants[0].getName();
        return match_participants[0].getName() + " vs " +
                match_participants[1].getName() + (finished ? " (" + scoreA + " - " + scoreB
                        + ")" : "");
    }

    public boolean contains(Match other) {
        if (other.match_participants[0] == match_participants[0] ||
                other.match_participants[1] == match_participants[1]
                || other.match_participants[1] == match_participants[0]
                || other.match_participants[0] == match_participants[1])
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scoreA, scoreB, finished, dummyMatch, endDate);
    }
}
