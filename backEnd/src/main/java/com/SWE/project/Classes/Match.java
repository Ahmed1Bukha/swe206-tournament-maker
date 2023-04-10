package com.SWE.project.Classes;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Match {
    private @Id @GeneratedValue long id;
    protected Participent participentA;
    protected Participent participentB;
    int scoreA, scoreB;
    Boolean finished;
    private Date endDate;

    Match(Participent participentA, Participent participentB) {
        this.participentA = participentA;
        this.participentB = participentB;
        finished = false;
    }

    public void enterResults(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        finished = true;
    }

    public Participent getWinner() {
        if (scoreA > scoreB)
            return participentA;
        return participentB;
    }

    public Participent getLoser() {
        if (scoreA < scoreB)
            return participentA;
        return participentB;
    }
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if(obj instanceof Match){
            Match other= (Match) obj;
            if(participentA==obj.participentA && obj.part)
        }
        return false;
    }
}


