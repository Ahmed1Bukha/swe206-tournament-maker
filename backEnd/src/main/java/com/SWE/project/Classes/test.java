package com.SWE.project.Classes;

import com.SWE.project.Enums.TOURNAMENT_TYPES;

public class test {
    public static void main(String[] args) {
        EliminationTournament robinTournament = new EliminationTournament("Test",
                4, null, null, 100,
                TOURNAMENT_TYPES.INDIVIDUAL);
        Student a = new Student(0L, "1");
        Student b = new Student(0L, "2");
        Student c = new Student(0L, "3");
        Student d = new Student(0L, "4");
        robinTournament.addParticipant(a);
        robinTournament.addParticipant(b);
        robinTournament.addParticipant(c);
        robinTournament.addParticipant(d);
        robinTournament.stopRegistration();
        robinTournament.generateMatches();
        robinTournament.start();

        while (!robinTournament.finished) {
            robinTournament.enterResults(1, 0);
        }
        System.out.println(robinTournament.allRounds);
        System.out.println(robinTournament.getCurrentPlayers());
        System.out.println(robinTournament.winner());
        System.out.println(robinTournament.winner().getTournaments());
    }
}
