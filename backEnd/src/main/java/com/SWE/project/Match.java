package com.SWE.project;

import java.sql.Date;

public class Match {
    protected Participent participentA;
    protected Participent participentB;
    int scoreA,scoreB;
    Boolean finished;
    private Date endDate;
    Match(Participent participentA,Participent participentB){
        this.participentA=participentA;
        this.participentB=participentB;
        finished= false;
    }
    public void enterResults(int scoreA,int scoreB){
        this.scoreA=scoreA;
        this.scoreB=scoreB;
        finished=true;
    }
    public Participent getWinner(){
        if(scoreA>scoreB) return participentA;
        return participentB;
    }
    public Participent getLoser(){
        if(scoreA<scoreB) return participentA;
        return participentB;
    }
}
interface Participent{}
