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
    Boolean dummyMatch; // dummy matches are used when team number are odd
    private Date endDate;

    Match(Participent participentA, Participent participentB) {
        
        this.participentA = participentA;
        this.participentB = participentB;
        finished = false;
        dummyMatch=false;
    }
    Match(Participent participentA){
        this.participentA = participentA;
        participentB=null;
        dummyMatch=true;
        finished = true;
    }
   


    public void enterResults(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        finished = true;
        
    }

    public Participent getWinner() {
        if(dummyMatch) return participentA;
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
       
        if(obj instanceof Match){
            Match other= (Match) obj;
            if((participentA==other.participentA && participentB==other.participentB)||(participentB==other.participentA && participentA==other.participentB)) return true;
            return false;
        }
        return false;
    }
    @Override
    public String toString() {
        if(dummyMatch) return participentA.getName() + " Dummy"; 
        return participentA.getName() + " "+ participentB.getName(); 
    }
    
    public boolean contains(Match other){
        if(other.participentA==participentA || other.participentB==participentB||other.participentB==participentA || other.participentA==participentB ) return true;
        return false;
    }
}


