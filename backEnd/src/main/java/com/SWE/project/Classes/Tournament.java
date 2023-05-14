package com.SWE.project.Classes;

import java.util.*;

import com.SWE.project.Enums.*;
import com.SWE.project.Exceptions.TournamentRegistrationClosedException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "Tournaments")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EliminationTournament.class, name = "EliminationTournament"),
        @JsonSubTypes.Type(value = RoundRobinTournament.class, name = "RoundRobinTournament")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@DiscriminatorColumn(name = "DISCRIM", discriminatorType = DiscriminatorType.STRING)
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Tournament {
    @Column
    @Id
    @GeneratedValue
    public Long id;

    @Column(name = "tournament_name")
    protected String name;

    @Column
    protected int participantCount;

    @Column
    protected Date startDate;

    @Column
    protected Date endDate;

    @Column
    protected double timeBetweenStages;
    @Column
    protected Participant winner;
    @Column
    protected TOURNAMENT_TYPES tournamentType;
    int indexOfCurrent;
    // @JsonIgnore
    @ManyToMany(targetEntity = com.SWE.project.Classes.Participant.class)
    @JoinTable(name = "tournament_participants", joinColumns = @JoinColumn(name = "tournament_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "ID"))
    @JsonIgnoreProperties({ "tournaments", "goalsMade", "goalsRecieved", "wins", "points", "matches", "teams" })
    protected Set<Participant> participants = new HashSet<Participant>();
    @OneToOne(targetEntity = com.SWE.project.Classes.Match.class)

    @JsonIgnoreProperties({ "match_participants", "tournament", "endDate" })
    protected Match currentMatch;

    @Column
    protected boolean open = true;

    @Column
    protected boolean finished = false;
    @OneToMany(targetEntity = com.SWE.project.Classes.Match.class)

    @JsonIgnoreProperties({ "match_participants", "tournament", "endDate" })
    protected List<Match> tournamentMatches = new ArrayList<>();

    @Column
    String sport;

    @Column
    int studentsPerTeam = 1;

    public ArrayList<String> generatedMatches = new ArrayList<>();

    protected Tournament(String name, int participantCount, int studentsPerTeam, Date startDate,
            Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType, String sport) {
        this.name = name;
        this.participantCount = participantCount;
        this.studentsPerTeam = tournamentType == TOURNAMENT_TYPES.INDIVIDUAL ? this.studentsPerTeam
                : studentsPerTeam;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeBetweenStages = timeBetweenStages;
        this.tournamentType = tournamentType;
        this.sport = sport;
        participants = new HashSet<>();
    }

    public Tournament() {

    }

    public void storeMatches() {
        generatedMatches= new ArrayList<>();
        for (Match t : tournamentMatches) {
            generatedMatches.add(t.stringMatch());
        }
        indexOfCurrent = tournamentMatches.indexOf(currentMatch);
    }

    public void startMatches(ArrayList<Match> array) {
        tournamentMatches = array;
        currentMatch = tournamentMatches.get(indexOfCurrent);
    }

    public abstract void start();

    public abstract void generateMatches();

    public abstract void enterResults(int firstScore, int secondScore);

    public void addParticipant(Participant x) {
        if (participantCount == participants.size() || !open) {
            throw new TournamentRegistrationClosedException(this.id);
        }

        switch (tournamentType) {
            case INDIVIDUAL -> {
                if (x instanceof Team)
                    throw new IllegalArgumentException("This is an individual's tournament");
            }
            case TEAM_BASED -> {
                if (x instanceof Student)
                    throw new IllegalArgumentException("This is a Team's tournament");
            }
        }
        participants.add(x);
        x.addTournament(this);
        if (participantCount == participants.size())
            start(); // Obviously incorrect. For testing purposes. In a real life
                     // scenario, it would also depend on the start date of the tournament.
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return this.sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getStudentsPerTeam() {
        return this.studentsPerTeam;
    }

    public void setStudentsPerTeam(int studentsPerTeam) {
        this.studentsPerTeam = studentsPerTeam;
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

    public Set<Participant> getParticipants() {
        return this.participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public Match getCurrentMatch() {
        return this.currentMatch;
    }

    public void setCurrentMatch(Match currentMatch) {
        this.currentMatch = currentMatch;
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean getFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Match> getTournamentMatches() {
        return this.tournamentMatches;
    }

    public void setTournamentMatches(List<Match> tournamentMatches) {
        this.tournamentMatches = tournamentMatches;
    }

    public int getParticipantCount() {
        return this.participantCount;
    }

    public void setParticipantCount(int num) {
        this.participantCount = num;
    }

    protected void stopRegistration() {
        open = false;
    }

    public List<String> getGeneratedMatches() {
        return this.generatedMatches;
    }

    public void setGeneratedMatches(ArrayList<String> generatedMatches) {
        this.generatedMatches = generatedMatches;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Tournament)) {
            return false;
        }
        Tournament tournament = (Tournament) o;
        return Objects.equals(name, tournament.name) && Objects.equals(startDate,
                tournament.startDate)
                && Objects.equals(endDate, tournament.endDate) && timeBetweenStages == tournament.timeBetweenStages
                && Objects.equals(tournamentType, tournament.tournamentType)
                && open == tournament.open
                && finished == tournament.finished
                && id == tournament.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, timeBetweenStages,
                tournamentType,
                open, finished);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", participantCount='" + getParticipantCount() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                ", timeBetweenStages='" + getTimeBetweenStages() + "'" +
                ", tournamentType='" + getTournamentType() + "'" +
                // ", indexOfCurrent='" + getIndexOfCurrent() + "'" +
                ", participants='" + getParticipants() + "'" +
                ", currentMatch='" + getCurrentMatch() + "'" +
                ", open='" + isOpen() + "'" +
                ", finished='" + isFinished() + "'" +
                ", tournamentMatches='" + getTournamentMatches() + "'" +
                ", sport='" + getSport() + "'" +
                ", studentsPerTeam='" + getStudentsPerTeam() + "'" +
                ", generatedMatches='" + getGeneratedMatches() + "'" +
                "}";
    }

}