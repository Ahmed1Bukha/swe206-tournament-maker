package com.SWE.project.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.SWE.project.Enums.TOURNAMENT_TYPES;
import com.SWE.project.Exceptions.TournamentRegistrationStillOpenException;


public class test {
    public static void main(String[] args) {
        RoundRobinTournament t= new RoundRobinTournament("r", 3, 1, null, null, 10000, TOURNAMENT_TYPES.INDIVIDUAL,"Football");
        // EliminationTournament elim = new EliminationTournament("Test",
        // 9, 1, null, null, 100,
        // TOURNAMENT_TYPES.INDIVIDUAL, "Football");
        Student a = new Student(0L, "1");
        Student b = new Student(0L, "2");
        Student c = new Student(0L, "3");
        // Student d = new Student(0L, "4");
        t.addParticipant(a);
        t.addParticipant(b);
        t.addParticipant(c);
        // round.addParticipant(d);
        t.start();
        
        System.out.println(t.generateRounds());
        ArrayList<ArrayList<Match>> rounds = t.generateRounds();
        
            List<Map<String,String>> result = new ArrayList<Map<String,String>>();
        
        for(int i=0;i<rounds.get(0).size();i++){
            HashMap<String,String> temp= new HashMap<>();
            int counter=1;
            for(ArrayList<Match> j: rounds){
               temp.put("Round "+counter, j.get(i).toString());
                counter++;
            }
            result.add(temp);
            temp=null;
        }
        System.out.println(result);
        // elim.addParticipant(a);
        // elim.addParticipant(b);
        // elim.addParticipant(c);
        // elim.addParticipant(d);
        // elim.addParticipant(e);
        // Student aa = new Student(0L, "1a");
        // Student ba = new Student(0L, "2a");
        // Student ca = new Student(0L, "3a");
        // Student da = new Student(0L, "4a");
        // elim.addParticipant(aa);
        // elim.addParticipant(ba);
        // elim.addParticipant(ca);
        // elim.addParticipant(da);

        // while (!elim.finished) {
        // elim.enterResults(1, 0);
        // }
        // System.out.println(elim.allRounds);
        // System.out.println(robinTournament.getCurrentPlayers());
        // System.out.println(robinTournament.winner().getName());
        // System.out.println(robinTournament.winner().getTournaments());
    }
}
