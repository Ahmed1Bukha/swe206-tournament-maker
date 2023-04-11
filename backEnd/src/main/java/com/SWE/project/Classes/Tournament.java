package com.SWE.project.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Tournaments")
public abstract class Tournament {
    @Column
    private @Id String name;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private double timeBetweenStages;
    @Column
    private TOURNAMENT_TYPES tournamentType;
    @Column
    protected Set<Participent> participents;
    
    protected Match currentMatch;
    protected boolean open=true;
    protected boolean finished=false;
    protected ArrayList<Match> tournamentMatches= new ArrayList<>();

    protected Tournament() {
    }

    protected Tournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeBetweenStages = timeBetweenStages;
        this.tournamentType = tournamentType;
        participents = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTimeBetweenStages() {
        return this.timeBetweenStages;
    }

    public void setTimeBetweenStages(double timeBetweenStages) {
        this.timeBetweenStages = timeBetweenStages;
    }

    public TOURNAMENT_TYPES getTournamentType() {
        return this.tournamentType;
    }

    public void setTournamentType(TOURNAMENT_TYPES tournamentType) {
        this.tournamentType = tournamentType;
    }
    protected void stopRegistration(){
        open=false;
    }
    abstract void start();
    
    abstract void generateMatches();

    abstract void enterResults(int firstScore,int secondScore);

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Tournament)) {
            return false;
        }
        Tournament tournament = (Tournament) o;
        return Objects.equals(name, tournament.name) && Objects.equals(startDate, tournament.startDate)
                && Objects.equals(endDate, tournament.endDate) && timeBetweenStages == tournament.timeBetweenStages
                && Objects.equals(tournamentType, tournament.tournamentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, timeBetweenStages, tournamentType);
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                ", timeBetweenStages='" + getTimeBetweenStages() + "'" +
                ", tournamentType='" + getTournamentType() + "'" +
                "}";
    }
    
    public void addParticipent(Participent x){
        if(!open) throw new IllegalArgumentException("Registiration finished");
        switch(tournamentType){
            case INDIVIDUAL->{
                    if(x instanceof Team) throw new IllegalArgumentException("This is an Indivisuls tournament");
                    participents.add(x);
                    
            }
            case TEAM_BASED->{
                if(x instanceof Student) throw new IllegalArgumentException("This is a Team's tournament");
                    participents.add(x);
        }

    }
}
}
class RoundRobinTournament extends Tournament {
    
    
    RoundRobinTournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        super(name, startDate, endDate, timeBetweenStages, tournamentType);
        
    }
    void start() {
        if(open) stopRegistration();
        currentMatch= tournamentMatches.get(0);
    }
    

    // public void generateMatches() {
    //     Object[] array = participents.toArray();
    //     for (int i = 0; i < array.length - 1; i++) {
    //         for (int j = i + 1; j < array.length; j++) {
    //             switch (getTournamentType()) {
    //                 case INDIVIDUAL -> tournamentMatches.add(new Match((Student) array[i], (Student) array[j]));
    //                 case TEAM_BASED -> tournamentMatches.add(new Match((Team) array[i], (Team) array[j]));
    //             }
    //         }
    //     }
    // }
    @Override
    void generateMatches() {
        Object[] array;
        if(!(participents.size() %2==0)){
            array= new Object[participents.size()+1];
            array[array.length-1]= null;
        }
        else{
            array= new Object[participents.size()];
        }
        Object[] temp= participents.toArray();
       for(int i=0;i<temp.length;i++){
        array[i]= temp[i];
       }
       int numberOfRounds= array.length-1;
       int numberOfMatchesPerRound=array.length/2;
       for(int i=0;i<numberOfRounds;i++){
        for(int j=0;j<numberOfMatchesPerRound;j++){
            switch (getTournamentType()) {
                case INDIVIDUAL -> {
                    Participent a = (Student) array[(i+j)%array.length];
                    Participent b = (Student) array[(i+array.length-j-1)%array.length];
                    if(a==null) tournamentMatches.add(new Match(b));
                    else if(b==null) tournamentMatches.add(new Match(a));
                    else tournamentMatches.add(new Match(a, b));
                }
                case TEAM_BASED -> {
                    Participent a = (Team) array[(i+j)%array.length];
                    Participent b = (Team) array[(i+array.length-j-1)%array.length];
                    if(a==null) tournamentMatches.add(new Match(b));
                    else if(b==null) tournamentMatches.add(new Match(a));
                    else tournamentMatches.add(new Match(a, b));
                }
            }
        }
       }
    }


    public void displayMatches(){
        for(Match i:tournamentMatches){
            System.out.println(i);
        }
    }

    @Override
    void enterResults(int winnerScore, int loserScore) {
        
        currentMatch.enterResults(winnerScore, loserScore);
        int index=tournamentMatches.indexOf(currentMatch);
        currentMatch.getWinner().win(winnerScore, loserScore);
        
        currentMatch.getLoser().lost(loserScore, winnerScore);
        if(!(index==tournamentMatches.size()-1)) currentMatch = tournamentMatches.get(index+1);
        else finished=true;
        if(currentMatch.dummyMatch){
            if(!(index+1==tournamentMatches.size()-1)) currentMatch = tournamentMatches.get(index+2);
            else finished=true;
        }
    }
    public void printPoints(){
        for(Participent i: participents){
           
            switch(getTournamentType()){
                case INDIVIDUAL->{
                        Student temp= (Student) i;
                        System.out.println(temp.getName()+" "+temp.points);
                }
                case TEAM_BASED->{
                    Team temp= (Team) i;
                    System.out.println(temp.getName()+" "+temp.points);
            }
            }
        }
    }
}

class EliminationTournament extends Tournament {

    EliminationTournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        super(name, startDate, endDate, timeBetweenStages, tournamentType);

    }
   
    @Override
    void start() {
        //TODO: do start
    }
    // TODO: finish generate matches
    public void generateMatches() {

    }
    @Override
    void enterResults(int firstScore, int secondScore) {
        // TODO Auto-generated method stub
        
    }
}

enum TOURNAMENT_TYPES {
    INDIVIDUAL, TEAM_BASED
}