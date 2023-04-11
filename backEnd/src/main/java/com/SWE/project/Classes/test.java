package com.SWE.project.Classes;

public class test {
    public static void main(String[] args) {
        RoundRobinTournament robinTournament= new RoundRobinTournament("Test", null, null, 100, TOURNAMENT_TYPES.INDIVIDUAL);
        Student a= new Student(0, "1", 0);
        Student b= new Student(0, "2", 0);
        Student c= new Student(0, "3", 0);
   
        robinTournament.addParticipent(a);
        robinTournament.addParticipent(b);
        robinTournament.addParticipent(c);
      
        robinTournament.stopRegistration();
        robinTournament.generateMatches();
        robinTournament.displayMatches();
        
    }
}
