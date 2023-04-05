package com.SWE.project.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import com.SWE.project.Enums.TOURNAMENT_TYPES;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tournaments")
public abstract class Tournament {
    @Column(name = "tournament_name")
    private @Id String name;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private double timeBetweenStages;

    @Column
    private TOURNAMENT_TYPES tournamentType;

    @OneToMany(mappedBy = "tournament")
    private Set<Team> ParticipantingTeams;

    @ManyToMany
    @JoinTable(name = "tournament_students", joinColumns = @JoinColumn(name = "tournament_name"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> ParticipantingStudents;

    private ArrayList<Match> tournamentMatches;

    protected Tournament() {
    }

    protected Tournament(String name, long startDate, long endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        this.name = name;
        this.startDate = new Date(System.currentTimeMillis() + startDate);
        this.endDate = new Date(System.currentTimeMillis() + endDate);
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

    public Set<Team> getParticipantingTeams() {
        return this.ParticipantingTeams;
    }

    public void setParticipantingTeams(Set<Team> ParticipantingTeams) {
        this.ParticipantingTeams = ParticipantingTeams;
    }

    public Set<Student> getParticipantingStudents() {
        return this.ParticipantingStudents;
    }

    public void setParticipantingStudents(Set<Student> ParticipantingStudents) {
        this.ParticipantingStudents = ParticipantingStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Tournament)) {
            return false;
        }
        Tournament tournament = (Tournament) o;
        return Objects.equals(name, tournament.name)
                && Objects.equals(startDate, tournament.startDate) && Objects.equals(endDate, tournament.endDate)
                && timeBetweenStages == tournament.timeBetweenStages
                && Objects.equals(tournamentType, tournament.tournamentType)
                && Objects.equals(ParticipantingTeams, tournament.ParticipantingTeams)
                && Objects.equals(ParticipantingStudents, tournament.ParticipantingStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, timeBetweenStages, tournamentType, ParticipantingTeams,
                ParticipantingStudents);
    }

    @Override
    public String toString() {
        return "{" +
                " tournament_name='" + getName() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                ", timeBetweenStages='" + getTimeBetweenStages() + "'" +
                ", tournamentType='" + getTournamentType() + "'" +
                ", ParticipantingTeams='" + getParticipantingTeams() + "'" +
                ", ParticipantingStudents='" + getParticipantingStudents() + "'" +
                "}";
    }

    public class RoundRobinTournament extends Tournament {
        HashMap<Participent, Integer> teamPoints;

        RoundRobinTournament(String name, long startDate, long endDate, double timeBetweenStages,
                TOURNAMENT_TYPES tournamentType) {
            super(name, startDate, endDate, timeBetweenStages, tournamentType);
            switch (getTournamentType()) {
                case INDIVIDUAL:
                    createPointMap(ParticipantingStudents);
                case TEAM_BASED:
                    createPointMap(ParticipantingTeams);
            }
        }

        private void createPointMap(Set<? extends Participent> participents) {
            for (Participent i : participents) {
                teamPoints.put(i, 0);
            }
        }

    }

    public class EliminationTournament extends Tournament {

        EliminationTournament(String name, long startDate, long endDate, double timeBetweenStages,
                TOURNAMENT_TYPES tournamentType) {
            super(name, startDate, endDate, timeBetweenStages, tournamentType);

        }

    }
}