package com.SWE.project.Classes;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Match {
    private @Id @GeneratedValue long id;
    protected Participant participantA;
    protected Participant participantB;
    int scoreA, scoreB;
    Boolean finished;
    Boolean dummyMatch; // dummy matches are used when team number are odd
    private Date endDate;

    Match(Participant participentA, Participant participentB) {
        this.participantA = participentA;
        this.participantB = participentB;
        finished = false;
        dummyMatch = false;
    }

    Match(Participant participantA) {
        this.participantA = participantA;
        participantB = null;
        dummyMatch = true;
        finished = true;
    }

    public void enterResults(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        finished = true;

    }

    public Participant getWinner() {
        if (scoreA > scoreB)
            return participantA;
        return participantB;
    }

    public Participant getLoser() {
        if (scoreA < scoreB)
            return participantA;
        return participantB;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Match) {
            Match other = (Match) obj;
            if ((participantA == other.participantA && participantB == other.participantB)
                    || (participantB == other.participantA && participantA == other.participantB))
                return true;
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        if (dummyMatch)
            return participantA.getName() + " Dummy";
        return participantA.getName() + " " + participantB.getName();
    }

    public boolean contains(Match other) {
        if (other.participantA == participantA || other.participantB == participantB
                || other.participantB == participantA || other.participantA == participantB)
            return true;
        return false;
    }
}
