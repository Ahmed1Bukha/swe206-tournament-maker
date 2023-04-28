package com.SWE.project.Classes;

import java.sql.Date;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.*;

@Entity
public class Match {
    @Id
    @GeneratedValue
    @Column(name = "match_id")
    private long id;

    @ManyToOne
    private Participant[] match_participants;

    @ManyToOne
    @OneToOne
    @MapsId
    @JoinColumn(name = "match_id")
    private Tournament tournament;

    int scoreA, scoreB;
    Boolean finished;
    Boolean dummyMatch; // dummy matches are used when team number are odd
    private Date endDate;

    public Match() {
    }

    Match(Participant[] match_participants, boolean dummyMatch) {
        this.match_participants= new Participant[2];
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

    public Participant getWinner() {
        if (dummyMatch)
            return match_participants[0];
        if (scoreA > scoreB)
            return match_participants[0];
        return match_participants[1];
    }

    public Participant getLoser() {
        if (scoreA < scoreB)
            return match_participants[0];
        return match_participants[1];
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
            return match_participants[0].getName() + " Dummy";
        return match_participants[0].getName() + " " +
                match_participants[1].getName();
    }

    public boolean contains(Match other) {
        if (other.match_participants[0] == match_participants[0] ||
                other.match_participants[1] == match_participants[1]
                || other.match_participants[1] == match_participants[0]
                || other.match_participants[0] == match_participants[1])
            return true;
        return false;
    }
}
