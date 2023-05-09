package com.SWE.project.Classes;

import java.util.*;

import com.SWE.project.Enums.*;
import com.SWE.project.Exceptions.TournamentRegistrationClosedException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(Views.Public.class)
    protected Long id;

    @Column(name = "tournament_name")
    @JsonView(Views.Public.class)
    protected String name;

    @Column
    protected int ParticipantCount;

    @Column
    @JsonView(Views.Public.class)
    protected Date startDate;

    @Column
    @JsonView(Views.Public.class)
    protected Date endDate;

    @Column
    @JsonView(Views.Public.class)
    protected double timeBetweenStages;

    @Column
    @JsonView(Views.Public.class)
    protected TOURNAMENT_TYPES tournamentType;

    // @JsonIgnore
    @JsonView(Views.Public.class)
    @ManyToMany(targetEntity = com.SWE.project.Classes.Participant.class)
    @JoinTable(name = "tournament_participants", joinColumns = @JoinColumn(name = "tournament_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "ID"))
    @JsonIgnoreProperties({ "tournaments", "goalsMade", "goalsRecieved", "wins", "points", "matches", "teams" })
    protected Set<Participant> participants = new HashSet<Participant>();

    @OneToOne(mappedBy = "tournament") // Possible issue
    @PrimaryKeyJoinColumn
    @JsonView(Views.Public.class)
    protected Match currentMatch;

    @Column
    @JsonView(Views.Public.class)
    protected boolean open = true;

    @Column
    @JsonView(Views.Public.class)
    protected boolean finished = false;

    @OneToMany(mappedBy = "tournament")
    @JsonView(Views.Public.class)
    protected List<Match> tournamentMatches = new ArrayList<>();

    // Game object

    protected Tournament(String name, int participantCount, Date startDate,
            Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        System.out.println("T C");
        this.name = name;
        this.ParticipantCount = participantCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeBetweenStages = timeBetweenStages;
        this.tournamentType = tournamentType;
        participants = new HashSet<>();
    }

    public Tournament() {

    }

    abstract void start();

    abstract void generateMatches();

    abstract void enterResults(int firstScore, int secondScore);

    public void addParticipant(Participant x) {
        if (ParticipantCount == participants.size() || !open) {
            this.open = false;
            throw new TournamentRegistrationClosedException(this.id);
        }
        switch (tournamentType) {
            case INDIVIDUAL -> {
                if (x instanceof Team)
                    throw new IllegalArgumentException("This is an individual's tournament");
                // participants.add(x);
            }
            case TEAM_BASED -> {
                if (x instanceof Student)
                    throw new IllegalArgumentException("This is a Team's tournament");
            }
        }
        participants.add(x);
        x.addTournament(this);
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

    protected void stopRegistration() {
        open = false;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("To e");
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
                && finished == tournament.finished;
    }

    @Override
    public int hashCode() {
        System.out.println("To hc");
        return Objects.hash(name, startDate, endDate, timeBetweenStages,
                tournamentType,
                open, finished);
    }

    @Override
    public String toString() {
        System.out.println("To ts");
        return "{" +
                "name='" + getName() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                ", timeBetweenStages='" + getTimeBetweenStages() + "'" +
                ", tournamentType='" + getTournamentType() + "'" +
                ", participants='" + getParticipants() + "'" +
                ", currentMatch='" + getCurrentMatch() + "'" +
                ", open='" + isOpen() + "'" +
                ", finished='" + isFinished() + "'" +
                ", tournamentMatches='" + getTournamentMatches() + "'" +
                "}";
    }
    //
}