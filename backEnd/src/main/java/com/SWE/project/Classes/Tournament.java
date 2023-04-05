package com.SWE.project.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Tournaments")
public abstract class Tournament {
    @Column
    private @Id String name;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private double timeBetweenStages;
    @Column
    private TOURNAMENT_TYPES tournamentType;
    @Column
    protected Set<Participent> ParticipantingTeams;
    @Column
    protected Set<Participent> ParticipantingStudents;
    protected ArrayList<Match> tournamentMatches;

    protected Tournament() {
    }

    protected Tournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeBetweenStages = timeBetweenStages;
        this.tournamentType = tournamentType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTimeBetweenStages() {
        return this.timeBetweenStages;
    }

    public void setTimeBetweenStages(double timeBetweenStages) {
        this.timeBetweenStages = timeBetweenStages;
    }

    public TOURNAMENT_TYPES getTournamentType() {
        return this.tournamentType;
    }

    public void setTournamentType(TOURNAMENT_TYPES tournamentType) {
        this.tournamentType = tournamentType;
    }

    abstract void generateMatches();

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Tournament)) {
            return false;
        }
        Tournament tournament = (Tournament) o;
        return Objects.equals(name, tournament.name) && Objects.equals(startDate, tournament.startDate)
                && Objects.equals(endDate, tournament.endDate) && timeBetweenStages == tournament.timeBetweenStages
                && Objects.equals(tournamentType, tournament.tournamentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, timeBetweenStages, tournamentType);
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                ", timeBetweenStages='" + getTimeBetweenStages() + "'" +
                ", tournamentType='" + getTournamentType() + "'" +
                "}";
    }
}

class RoundRobinTournament extends Tournament {
    HashMap<Participent, Integer> teamPoints;

    RoundRobinTournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        super(name, startDate, endDate, timeBetweenStages, tournamentType);
        switch (getTournamentType()) {
            case INDIVIDUAL -> createPointMap(ParticipantingStudents);
            case TEAM_BASED -> createPointMap(ParticipantingTeams);
        }
    }

    private void createPointMap(Set<Participent> participents) {
        for (Participent i : participents) {
            teamPoints.put(i, 0);
        }
    }

    public void generateMatches() {
        Object[] array = teamPoints.keySet().toArray();
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                switch (getTournamentType()) {
                    case INDIVIDUAL -> tournamentMatches.add(new Match((Student) array[i], (Student) array[j]));
                    case TEAM_BASED -> tournamentMatches.add(new Match((Team) array[i], (Team) array[j]));
                }
            }
        }
    }

}

class EliminationTournament extends Tournament {

    EliminationTournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        super(name, startDate, endDate, timeBetweenStages, tournamentType);

    }

    // TODO: finish generate matches
    public void generateMatches() {

    }
}

enum TOURNAMENT_TYPES {
    INDIVIDUAL, TEAM_BASED
}