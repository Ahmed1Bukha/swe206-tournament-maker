package com.SWE.project.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.SWE.project.Enums.TOURNAMENT_TYPES;
import com.SWE.project.Exceptions.TournamentRegistrationStillOpenException;
import services.Courier;
import services.SendService;
import models.SendEnhancedRequestBody;
import models.SendEnhancedResponseBody;
import models.SendRequestMessage;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
public class test {
    public static void main(String[] args) {
        RoundRobinTournament g= new RoundRobinTournament("r", 3, 1, null, null, 10000, TOURNAMENT_TYPES.INDIVIDUAL,"Football");
        g.id=2L;
        // EliminationTournament elim = new EliminationTournament("Test",
        // 9, 1, null, null, 100,
        // TOURNAMENT_TYPES.INDIVIDUAL, "Football");
        Student a = new Student(0L, "1");
        Student b = new Student(0L, "2");
        Student c = new Student(0L, "3");
        // Student d = new Student(0L, "4");
        g.addParticipant(a);
        g.addParticipant(b);
        g.addParticipant(c);
        // round.addParticipant(d);
        g.start();
        
        System.out.println(g.generateRounds());
        while(!g.finished){
            g.enterResults(1, 0);
        }
        System.out.println(g.getWinner());
        System.out.println(g.findLeaderBoard(g));
        RoundRobinTournament t= new RoundRobinTournament("r", 3, 1, null, null, 10000, TOURNAMENT_TYPES.INDIVIDUAL,"Football");
        t.id=1L;
        // EliminationTournament elim = new EliminationTournament("Test",
        // 9, 1, null, null, 100,
        // TOURNAMENT_TYPES.INDIVIDUAL, "Football");
       
        // Student d = new Student(0L, "4");
        t.addParticipant(a);
        t.addParticipant(b);
        t.addParticipant(c);
        // round.addParticipant(d);
        t.start();
        
        System.out.println(t.generateRounds());
        while(!t.finished){
            t.enterResults(1, 0);
        }
        System.out.println(t.getWinner());
        System.out.println(t.findLeaderBoard(t));
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
