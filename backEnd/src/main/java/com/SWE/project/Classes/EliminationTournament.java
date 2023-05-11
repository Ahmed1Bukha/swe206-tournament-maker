package com.SWE.project.Classes;

import java.util.Date;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.SWE.project.Enums.TOURNAMENT_TYPES;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.Objects;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = EliminationTournament.class)
@DiscriminatorValue("ET")
@JsonTypeName("ET")
public class EliminationTournament extends Tournament {
    @OneToMany(targetEntity = com.SWE.project.Classes.Match.class)
    List<Set<Match>> allRounds = new ArrayList<Set<Match>>();

    @ManyToMany // WTF? i have never been more confused in my whole life
    @JoinTable(name = "elimination_tournament_current_participants", joinColumns = @JoinColumn(name = "tournament_id"), inverseJoinColumns = @JoinColumn(name = "participant_id"))
    // @JsonIgnoreProperties({""})
    List<Participant> currentPlayers = new ArrayList<>();

    @Column
    Integer remainingMatchesInRound;

    public EliminationTournament() {
    }

    public EliminationTournament(String name, int participantCount, int studentsPerTeam, long startDate, long endDate,
            double timeBetweenStages,
            String tournamentType, String sport) {
        super(name, participantCount, studentsPerTeam, new Date(startDate), new Date(endDate), timeBetweenStages,
                tournamentType == "INDIVIDUAL" ? TOURNAMENT_TYPES.INDIVIDUAL : TOURNAMENT_TYPES.TEAM_BASED, sport);
    }

    public EliminationTournament(String name, int participantCount, int studentsPerTeam, Date startDate, Date endDate,
            double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType, String sport) {
        super(name, participantCount, studentsPerTeam, startDate, endDate, timeBetweenStages, tournamentType, sport);
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
        if (allRounds.size() == 0) {
            Collections.shuffle(temp);
        }
        for (int i = 0; i < temp.size(); i += 2) {
            int num1 = i;
            int num2 = i + 1;
            if (temp.get(num1) != null && temp.get(num2) != null) {
                matchUps.add(new Match(new Participant[] { temp.get(num1), temp.get(num2) },
                        false));
            } else if (temp.get(num1) != null) {
                matchUps.add(new Match(new Participant[] { temp.get(num1), null }, true));

            } else {
                matchUps.add(new Match(new Participant[] { temp.get(num2), null }, true));
            }
        }
        tournamentMatches = new ArrayList<>(matchUps);
        allRounds.add(matchUps);
        remainingMatchesInRound = tournamentMatches.size();
    }

    @Override
    public void start() {
        if (open)
            stopRegistration();

        currentMatch = tournamentMatches.get(0);
        remainingMatchesInRound--;
    }

    @Override
    public void stopRegistration() {
        open = false;
        currentPlayers.addAll(participants);
    }

    @Override
    public void enterResults(int firstScore, int secondScore) {

        currentMatch.enterResults(firstScore, secondScore);
        int index = tournamentMatches.indexOf(currentMatch);
        currentPlayers.remove(currentMatch.decideLoser());

        if (remainingMatchesInRound > 0) {
            remainingMatchesInRound--;
            if (!tournamentMatches.get(index + 1).dummyMatch) {
                currentMatch = tournamentMatches.get(index + 1);
            } else {
                currentPlayers.remove(tournamentMatches.get(index + 1).decideLoser());
                remainingMatchesInRound--;

                if (remainingMatchesInRound > 0) {
                    currentMatch = tournamentMatches.get(index + 2);
                } else {
                    generateMatches();
                    currentMatch = tournamentMatches.get(0);
                    remainingMatchesInRound--;
                }
            }
        } else {
            generateMatches();
            currentMatch = tournamentMatches.get(0);
            remainingMatchesInRound--;
        }
    }

    public Participant winner() {
        if (finished) {
            return currentPlayers.get(0);
        }
        throw new IllegalAccessError("Unfinished tournament");
    }

    public Participant secondPlace() {
        if (finished) {
            Match lastRound = (Match) allRounds.get(allRounds.size() - 1).toArray()[0];
            return lastRound.decideLoser();
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

    public Integer getRemainingMatchesInRound() {
        return this.remainingMatchesInRound;
    }

    public void setRemainingMatchesInRound(Integer remainingMatchesInRound) {
        this.remainingMatchesInRound = remainingMatchesInRound;
    }

    @Override
    public String toString() {
        return "{" +
                super.toString().substring(1, super.toString().length() - 1) +
                ", allRounds='" + getAllRounds() + "'" +
                // ", currentPlayers='" + getCurrentPlayers() + "'" +
                ", remainingMatchesInRound='" + getRemainingMatchesInRound() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
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
        return 31 * super.hashCode() + Objects.hashCode(remainingMatchesInRound);
    }
}