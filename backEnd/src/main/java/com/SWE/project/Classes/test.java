package com.SWE.project.Classes;

import com.SWE.project.Enums.TOURNAMENT_TYPES;

public class test {
    public static void main(String[] args) {
        EliminationTournament robinTournament = new EliminationTournament("Test",
                8, null, null, 100,
                TOURNAMENT_TYPES.INDIVIDUAL);
        Student a = new Student(0L, "1");
        Student b = new Student(0L, "2");
        Student c = new Student(0L, "3");
        Student d = new Student(0L, "4");
        robinTournament.addParticipant(a);
        robinTournament.addParticipant(b);
        robinTournament.addParticipant(c);
        robinTournament.addParticipant(d);
        Student aa = new Student(0L, "1a");
        Student ba = new Student(0L, "2a");
        Student ca = new Student(0L, "3a");
        Student da = new Student(0L, "4a");
        robinTournament.addParticipant(aa);
        robinTournament.addParticipant(ba);
        robinTournament.addParticipant(ca);
        robinTournament.addParticipant(da);
        robinTournament.stopRegistration();
        robinTournament.generateMatches();
        robinTournament.start();

        // while (!robinTournament.finished) {
        robinTournament.enterResults(1, 0);
        // }
        System.out.println(robinTournament.allRounds);
        // System.out.println(robinTournament.getCurrentPlayers());
        // System.out.println(robinTournament.winner().getName());
        // System.out.println(robinTournament.winner().getTournaments());
    }
}
