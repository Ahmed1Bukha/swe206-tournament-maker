package com.SWE.project.Classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.SWE.project.Enums.TOURNAMENT_TYPES;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import java.util.Objects;

@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = RoundRobinTournament.class)
@DiscriminatorValue("RRT")
@JsonTypeName("RRT")
public class RoundRobinTournament extends Tournament {
    @OneToMany(targetEntity = com.SWE.project.Classes.Participant.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ElementCollection
    @CollectionTable(name = "team_points", joinColumns = @JoinColumn(name = "tournament_id"))
    @Column(name = "points")
    @MapKeyJoinColumn(name = "team_id")
    private Map<Participant, Integer> teamPoints = new HashMap<Participant, Integer>();

    public RoundRobinTournament() {
    }

    public RoundRobinTournament(String name, int participantCount, int studentsPerTeam, int[] startDate, int[] endDate,
            double timeBetweenStages,
            String tournamentType, String sport) {
        super(name, participantCount, studentsPerTeam, new Date(startDate[2], startDate[1], startDate[0]),
                new Date(endDate[2], endDate[1], endDate[0]), timeBetweenStages,
                tournamentType.equals("INDIVIDUAL") ? TOURNAMENT_TYPES.INDIVIDUAL : TOURNAMENT_TYPES.TEAM_BASED, sport);
    }

    public RoundRobinTournament(String name, int participantCount, int studentsPerTeam, Date startDate, Date endDate,
            double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType, String sport) {
        super(name, participantCount, studentsPerTeam, startDate, endDate, timeBetweenStages, tournamentType, sport);
    }

    public void start() {
        if (open) {
            stopRegistration();
            generateMatches();
            currentMatch = tournamentMatches.get(0);
        }
    }

    @Override
    public void generateMatches() {
        ArrayList<Participant> array = new ArrayList<>(participants);
        if (!(participants.size() % 2 == 0)) {
            array.add(null);
        }
        int numberOfRounds = array.size() - 1;
        int numberOfMatchesPerRound = array.size() / 2;
        Participant firstTeam = array.get(0);
        array.remove(0);

        for (int i = 0; i < numberOfRounds; i++) {
            int specialIndex = i % array.size();
            Participant b = (Student) array.get((specialIndex));

            if (b == null)
                tournamentMatches.add(new Match(new Participant[] { firstTeam }, true));
            else
                tournamentMatches
                        .add(new Match(new Participant[] {
                                firstTeam, b }, false));

            for (int j = 1; j < numberOfMatchesPerRound; j++) {
                Participant a = array.get((i + j) % array.size());
                b = array.get((i + array.size() - j) % array.size());

                if (a == null)
                    tournamentMatches.add(
                            new Match(new Participant[] { b, null },
                                    true));
                else if (b == null)
                    tournamentMatches.add(
                            new Match(new Participant[] { a, null },
                                    true));

                else
                    tournamentMatches
                            .add(new Match(new Participant[] { a, b },
                                    false));
            }
        }
    }

    public ArrayList<ArrayList<Match>> generateRounds() {
        ArrayList<ArrayList<Match>> temp = new ArrayList<>();
        int numberOfRounds, numberOfMatchesPerRound;
        if (!(participants.size() % 2 == 0)) {
            numberOfRounds = participants.size();
            numberOfMatchesPerRound = participants.size() / 2 + 1;
        } else {
            numberOfRounds = participants.size() - 1;
            numberOfMatchesPerRound = participants.size() / 2;

        }
        for (int i = 0; i < numberOfRounds; i++) {
            temp.add(new ArrayList<Match>());
            for (int j = 0; j < numberOfMatchesPerRound; j++) {
                temp.get(i).add(tournamentMatches.get(j));
            }
        }
        return temp;
    }

    public void displayMatches() {
        for (Match i : tournamentMatches) {
            System.out.println(i);
        }
    }

    @Override
    public void enterResults(int winnerScore, int loserScore) {
        currentMatch.enterResults(winnerScore, loserScore);
        int index = tournamentMatches.indexOf(currentMatch);
        currentMatch.decideWinner().win(winnerScore, loserScore);

        currentMatch.decideLoser().lost(loserScore, winnerScore);
        if (!(index == tournamentMatches.size() - 1))
            currentMatch = tournamentMatches.get(index + 1);
        else
            finished = true;
        if (currentMatch.dummyMatch) {
            if (!(index + 1 == tournamentMatches.size() - 1))
                currentMatch = tournamentMatches.get(index + 2);
            else
                finished = true;
        }
    }

    public void printPoints() {
        for (Participant i : participants) {

            switch (getTournamentType()) {
                case INDIVIDUAL -> {
                    Student temp = (Student) i;
                    System.out.println(temp.getName() + " " + temp.points);
                }
                case TEAM_BASED -> {
                    Team temp = (Team) i;
                    System.out.println(temp.getName() + " " + temp.points);
                }
            }
        }
    }

    public Participant findWinner() {
        Comparator<Participant> poinComparator = new Comparator<Participant>() {
            public int compare(Participant a, Participant b) {
                return a.getPoints() - b.getPoints();
            }

        };
        ArrayList<Participant> array = new ArrayList<>(participants);
        array.sort(poinComparator);
        return array.get(array.size() - 1);
    }

    public ArrayList<Participant> findLeaderBoard() {
        Comparator<Participant> poinComparator = new Comparator<Participant>() {
            public int compare(Participant a, Participant b) {
                return a.getPoints() - b.getPoints();
            }
        };
        ArrayList<Participant> array = new ArrayList<>(participants);
        array.sort(poinComparator);
        return array;
    }

    public Map<Participant, Integer> getTeamPoints() {
        return this.teamPoints;
    }

    public void setTeamPoints(Map<Participant, Integer> teamPoints) {
        this.teamPoints = teamPoints;
    }

    @Override
    public String toString() {
        return "{" +
                super.toString().substring(1, super.toString().length() - 1) +
                // ", teamPoints='" + getTeamPoints() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RoundRobinTournament)) {
            return false;
        }
        RoundRobinTournament roundRobinTournament = (RoundRobinTournament) o;
        return Objects.equals(teamPoints, roundRobinTournament.teamPoints);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}