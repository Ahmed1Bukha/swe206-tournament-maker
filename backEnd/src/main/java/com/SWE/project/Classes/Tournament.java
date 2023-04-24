package com.SWE.project.Classes;

import java.lang.reflect.Method;
import java.util.*;

import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.SWE.project.Enums.*;

import jakarta.persistence.*;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToMany
    @JoinTable(name = "tournament_participants", joinColumns = @JoinColumn(name = "tournament_name"), inverseJoinColumns = @JoinColumn(name = "participant_id"))
    protected Set<Participant> participants; // Done

    @OneToOne(mappedBy = "tournament") // Possible issue
    @PrimaryKeyJoinColumn
    protected Match currentMatch;

    @Column
    protected boolean open = true;

    @Column
    protected boolean finished = false;

    @OneToMany(mappedBy = "tournament")
    protected ArrayList<Match> tournamentMatches = new ArrayList<>();

    // Game object

    public Tournament() {
    }

    protected Tournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeBetweenStages = timeBetweenStages;
        this.tournamentType = tournamentType;
        participants = new HashSet<>();
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

    public ArrayList<Match> getTournamentMatches() {
        return this.tournamentMatches;
    }

    public void setTournamentMatches(ArrayList<Match> tournamentMatches) {
        this.tournamentMatches = tournamentMatches;
    }

    protected void stopRegistration() {
        open = false;
    }

    abstract void start();

    abstract void generateMatches();

    abstract void enterResults(int firstScore, int secondScore);

    public void addParticipant(Participant x) {
        if (!open)
            throw new IllegalArgumentException("Registiration finished");
        switch (tournamentType) {
            case INDIVIDUAL -> {
                if (x instanceof Team)
                    throw new IllegalArgumentException("This is an individual's tournament");
                participants.add(x);

            }
            case TEAM_BASED -> {
                if (x instanceof Student)
                    throw new IllegalArgumentException("This is a Team's tournament");
                participants.add(x);
            }

        }
        // x.addTournament(this);
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
                && Objects.equals(participants, tournament.participants)
                && Objects.equals(currentMatch, tournament.currentMatch) && open == tournament.open
                && finished == tournament.finished && Objects.equals(tournamentMatches,
                        tournament.tournamentMatches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, timeBetweenStages,
                tournamentType, participants, currentMatch,
                open, finished, tournamentMatches);
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
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

class RoundRobinTournament extends Tournament {
    private HashMap<Participant, Integer> teamPoints;

    RoundRobinTournament(String name, Date startDate, Date endDate, double timeBetweenStages,
            TOURNAMENT_TYPES tournamentType) {
        super(name, startDate, endDate, timeBetweenStages, tournamentType);

    }

    void start() {
        if (open)
            stopRegistration();
        currentMatch = tournamentMatches.get(0);
    }

    @Override
    void generateMatches() {
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
                tournamentMatches.add(new Match(new Participant[] { firstTeam, b }, false));

            for (int j = 1; j < numberOfMatchesPerRound; j++) {
                Participant a = array.get((i + j) % array.size());
                b = array.get((i + array.size() - j) % array.size());

                if (a == null)
                    tournamentMatches.add(new Match(new Participant[] { b }, true));
                else if (b == null)
                    tournamentMatches.add(new Match(new Participant[] { a }, true));

                else
                    tournamentMatches.add(new Match(new Participant[] { a, b }, false));

            }
        }
    }

    public ArrayList<ArrayList<Match>> getRounds() {
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
    void enterResults(int winnerScore, int loserScore) {

        currentMatch.enterResults(winnerScore, loserScore);
        int index = tournamentMatches.indexOf(currentMatch);
        currentMatch.getWinner().win(winnerScore, loserScore);

        currentMatch.getLoser().lost(loserScore, winnerScore);
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

    public Participant getWinner() {
        Comparator<Participant> poinComparator = new Comparator<Participant>() {
            public int compare(Participant a, Participant b) {
                return a.getPoints() - b.getPoints();
            }

        };
        ArrayList<Participant> array = new ArrayList<>(participants);
        array.sort(poinComparator);
        return array.get(array.size() - 1);
    }

    public ArrayList<Participant> getLeaderBoard() {
        Comparator<Participant> poinComparator = new Comparator<Participant>() {
            public int compare(Participant a, Participant b) {
                return a.getPoints() - b.getPoints();
            }
        };
        ArrayList<Participant> array = new ArrayList<>(participants);
        array.sort(poinComparator);
        return array;
    }
}

class EliminationTournament extends Tournament {

    ArrayList<Set<Match>> allRounds = new ArrayList<>();
    int remainingMatchesInRound;
    ArrayList<Participant> currentPlayers = new ArrayList<>();

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
                matchUps.add(new Match(new Participant[] { temp.get(num1), temp.get(num2) }, false));
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
}