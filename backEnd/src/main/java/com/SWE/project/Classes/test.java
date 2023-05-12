package com.SWE.project.Classes;

import com.SWE.project.Enums.TOURNAMENT_TYPES;

public class test {
    public static void main(String[] args) {
        EliminationTournament elim = new EliminationTournament("Test",
                9, 1, null, null, 100,
                TOURNAMENT_TYPES.INDIVIDUAL, "Football");
        Student a = new Student(0L, "1");
        Student b = new Student(0L, "2");
        Student c = new Student(0L, "3");
        Student d = new Student(0L, "4");
        Student e = new Student(0L, "5");
        elim.addParticipant(a);
        elim.addParticipant(b);
        elim.addParticipant(c);
        elim.addParticipant(d);
        elim.addParticipant(e);
        Student aa = new Student(0L, "1a");
        Student ba = new Student(0L, "2a");
        Student ca = new Student(0L, "3a");
        Student da = new Student(0L, "4a");
        elim.addParticipant(aa);
        elim.addParticipant(ba);
        elim.addParticipant(ca);
        elim.addParticipant(da);
        elim.stopRegistration();
        elim.generateMatches();
        elim.start();

        // while (!robinTournament.finished) {
        // elim.enterResults(1, 0);
        // }
        System.out.println(elim.allRounds);
        // System.out.println(robinTournament.getCurrentPlayers());
        // System.out.println(robinTournament.winner().getName());
        // System.out.println(robinTournament.winner().getTournaments());
    }
}
