package com.SWE.project.Classes;

import java.sql.Date;

import jakarta.persistence.*;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Match {
    @Id
    @GeneratedValue
    @Column(name = "match_id")
    @JsonView(Views.Public.class)
    private Long id;

    @ManyToOne
    @JsonView(Views.Public.class)
    private Participant[] match_participants;

    @ManyToOne
    @OneToOne
    @MapsId
    @JoinColumn(name = "match_id")
    @JsonView(Views.Internal.class)
    private Tournament tournament;

    @JsonView(Views.Public.class)
    @Column
    Integer scoreA;

    @JsonView(Views.Public.class)
    @Column
    Integer scoreB;

    @JsonView(Views.Public.class)
    @Column
    boolean finished;

    @JsonView(Views.Public.class)
    @Column
    boolean dummyMatch; // dummy matches are used when team number are odd

    @JsonView(Views.Public.class)
    @Column
    private Date endDate;

    public Match() {
    }

    public Match(long id, Participant[] match_participants, Tournament tournament, int scoreA, int scoreB,
            Boolean finished, Boolean dummyMatch, Date endDate) {
        this.id = id;
        this.match_participants = match_participants;
        this.tournament = tournament;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.finished = finished;
        this.dummyMatch = dummyMatch;
        this.endDate = endDate;
    }

    Match(Participant[] match_participants, boolean dummyMatch) {
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

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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

    public Boolean isFinished() {
        return this.finished;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean isDummyMatch() {
        return this.dummyMatch;
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

    @Override
    public boolean equals(Object obj) {
        System.out.println("M e");
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
        System.out.println("M ts");
        if (dummyMatch)
            return match_participants[0].getName() + " Dummy";
        return match_participants[0].getName() + " " +
                match_participants[1].getName();
    }

    public boolean contains(Match other) {
        System.out.println("M c");
        if (other.match_participants[0] == match_participants[0] ||
                other.match_participants[1] == match_participants[1]
                || other.match_participants[1] == match_participants[0]
                || other.match_participants[0] == match_participants[1])
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        System.out.println("M hc");
        return Objects.hash(id, scoreA, scoreB, finished, dummyMatch, endDate);
    }
}
