// package com.SWE.project.Classes;

// import com.SWE.project.Enums.TOURNAMENT_TYPES;

// public class test {
// public static void main(String[] args) {
// RoundRobinTournament robinTournament = new RoundRobinTournament("Test", null,
// null, 100,
// TOURNAMENT_TYPES.INDIVIDUAL);
// Student a = new Student(0, "1");
// Student b = new Student(0, "2");
// Student c = new Student(0, "3");
// Student d = new Student(0, "4");
// robinTournament.addParticipant(a);
// robinTournament.addParticipant(b);
// robinTournament.addParticipant(c);
// robinTournament.addParticipant(d);
// robinTournament.stopRegistration();
// robinTournament.generateMatches();
// robinTournament.start();
// while (!robinTournament.finished) {
// robinTournament.enterResults(1, 0);
// }
// robinTournament.printPoints();

// System.out.println(robinTournament.getWinner());
// }
// }
