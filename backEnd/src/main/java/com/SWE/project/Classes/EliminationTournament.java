package com.SWE.project.Classes;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.SWE.project.Enums.TOURNAMENT_TYPES;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.Objects;

@Entity
public class EliminationTournament extends Tournament {
    @OneToMany(targetEntity = com.SWE.project.Classes.Match.class)
    List<Set<Match>> allRounds = new ArrayList<Set<Match>>();

    @ManyToMany // WTF? i have never been more confused in my whole life
    @JoinTable(name = "elimination_tournament_current_participants", joinColumns = @JoinColumn(name = "tournament_name"), inverseJoinColumns = @JoinColumn(name = "participant_id"))
    List<Participant> currentPlayers = new ArrayList<>();

    @Column
    int remainingMatchesInRound;

    public EliminationTournament() {
    }

    public EliminationTournament(String name, long startDate, long endDate, double timeBetweenStages,
            String tournamentType) {
        super(name, new Date(startDate), new Date(endDate), timeBetweenStages,
                tournamentType == "INDIVIDUAL" ? TOURNAMENT_TYPES.INDIVIDUAL : TOURNAMENT_TYPES.TEAM_BASED);
    }

    EliminationTournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        super(name, startDate, endDate, timeBetweenStages, tournamentType);
    }

    public void generateMatches() {
        Set<Match> matchUps = new HashSet<>();
        Set<Participant> set = new HashSet<>(currentPlayers);
        if (currentPlayers.size() == 1) {
            finished = true;
            return;
        }
        if (set.size() % 2 == 1)
            set.add(null);
        ArrayList<Participant> temp = new ArrayList<>(set);
        while (matchUps.size() != set.size() / 2) {
            int num1 = (int) (Math.random() * set.size());
            int num2 = (int) (Math.random() * set.size());

            if (num1 == num2)
                continue;
            if (temp.get(num1) != null && temp.get(num2) != null) {
                matchUps.add(new Match(new Participant[] { temp.get(num1), temp.get(num2) },
                        false));
            } else if (temp.get(num1) != null) {
                matchUps.add(new Match(new Participant[] { temp.get(num1) }, true));
            } else {
                matchUps.add(new Match(new Participant[] { temp.get(num2) }, true));
            }

        }
        tournamentMatches = new ArrayList<>(matchUps);
        allRounds.add(matchUps);
        remainingMatchesInRound = tournamentMatches.size();
    }

    @Override
    void start() {
        if (open)
            stopRegistration();

        currentMatch = tournamentMatches.get(0);
        remainingMatchesInRound--;
    }

    @Override
    protected void stopRegistration() {
        open = false;
        currentPlayers.addAll(participants);
    }

    @Override
    void enterResults(int firstScore, int secondScore) {
        // TODO Auto-generated method stub

        currentMatch.enterResults(firstScore, secondScore);
        int index = tournamentMatches.indexOf(currentMatch);
        currentPlayers.remove(currentMatch.getLoser());
        if (remainingMatchesInRound > 0) {
            remainingMatchesInRound--;
            if (!tournamentMatches.get(index + 1).dummyMatch) {
                currentMatch = tournamentMatches.get(index + 1);
            }
            remainingMatchesInRound--;
            if (remainingMatchesInRound > 0) {
                currentMatch = tournamentMatches.get(index + 2);
            }
            generateMatches();
            currentMatch = tournamentMatches.get(0);
            remainingMatchesInRound--;
        } else {
            generateMatches();
            currentMatch = tournamentMatches.get(0);
            remainingMatchesInRound--;
        }
    }

    public Participant getWinner() {
        if (finished) {
            return currentPlayers.get(0);
        }
        throw new IllegalAccessError("Unfinished tournament");
    }

    public Participant getSecondPlace() {
        if (finished) {
            Match lastRound = (Match) allRounds.get(allRounds.size() - 1).toArray()[0];
            return lastRound.getLoser();
        }
        throw new IllegalAccessError("Unfinished tournament");
    }

    public List<Set<Match>> getAllRounds() {
        return this.allRounds;
    }

    public void setAllRounds(List<Set<Match>> allRounds) {
        this.allRounds = allRounds;
    }

    public List<Participant> getCurrentPlayers() {
        return this.currentPlayers;
    }

    public void setCurrentPlayers(List<Participant> currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public int getRemainingMatchesInRound() {
        return this.remainingMatchesInRound;
    }

    public void setRemainingMatchesInRound(int remainingMatchesInRound) {
        this.remainingMatchesInRound = remainingMatchesInRound;
    }

    @Override
    public String toString() {
        System.out.println("ET ts");
        return "{" +
                super.toString().substring(1, super.toString().length() - 1) +
                ", allRounds='" + getAllRounds() + "'" +
                // ", currentPlayers='" + getCurrentPlayers() + "'" +
                ", remainingMatchesInRound='" + getRemainingMatchesInRound() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("ET e");
        if (o == this)
            return true;
        if (!(o instanceof EliminationTournament)) {
            return false;
        }
        EliminationTournament eliminationTournament = (EliminationTournament) o;
        return remainingMatchesInRound == eliminationTournament.remainingMatchesInRound;
    }

    @Override
    public int hashCode() {
        System.out.println("ET hc");
        return 31 * super.hashCode() + Objects.hashCode(remainingMatchesInRound);
    }
}