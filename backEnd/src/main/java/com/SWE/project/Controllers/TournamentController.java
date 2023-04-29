package com.SWE.project.Controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SWE.project.Classes.*;
import com.SWE.project.Exceptions.*;
import com.SWE.project.Repositories.*;

@RestController
public class TournamentController {

    @Autowired
    private final TournamentRepo tournamentRepo;

    @Autowired
    private final RoundRobinTournamentRepo roundRobinRepo;

    @Autowired
    private final EliminationTournamentRepo eliminationRepo;

    @Autowired
    private final StudentRepo studentRepo;

    @Autowired
    private final TeamRepo teamRepo;

    @Autowired
    private final ParticipantRepo participantRepo;

    public TournamentController(TournamentRepo tournamentRepo, RoundRobinTournamentRepo roundRobinRepo,
            EliminationTournamentRepo eliminationRepo, StudentRepo studentRepo, TeamRepo teamRepo,
            ParticipantRepo participantRepo) {
        this.tournamentRepo = tournamentRepo;
        this.roundRobinRepo = roundRobinRepo;
        this.eliminationRepo = eliminationRepo;
        this.studentRepo = studentRepo;
        this.teamRepo = teamRepo;
        this.participantRepo = participantRepo;
    }

    @GetMapping("/Tournaments")
    List<Tournament> allTournaments() {
        // String str = "{";
        // for (Tournament t : tournamentRepo.findAll()) {
        // str = str.concat(t.toString());
        // }
        // return str.concat("}");
        return tournamentRepo.findAll();
    }

    @GetMapping("/Tournaments/getMatches/{TournamentId}")
    String getMatches(@PathVariable("TournamentId") Long id) {
        String str = "{";
        Optional<Tournament> temp = tournamentRepo.findById(id);
        if (temp.isEmpty())
            throw new TournamentNotFoundException(id);

        for (Match m : temp.get().getTournamentMatches()) {
            str = str.concat(m.toString());
        }
        return str.concat("}");
    }

    @GetMapping("/Tournaments/getParticipants/{TournamentId}")
    String getParticipants(@PathVariable("TournamentId") long id) {
        String str = "{";
        Optional<Tournament> temp = tournamentRepo.findById(id);
        if (temp.isEmpty())
            throw new TournamentNotFoundException(id);

        for (Participant p : temp.get().getParticipants()) {
            str = str.concat(p.toString());
        }
        return str.concat("}");
    }

    @PostMapping("/RoundRobinTournaments")
    String newTournament(@RequestBody RoundRobinTournament newTournament) {
        return roundRobinRepo.save(newTournament).toString();
    }

    @PostMapping("/EliminationTournaments")
    String newTournament(@RequestBody EliminationTournament newTournament) {
        return eliminationRepo.save(newTournament).toString();
    }

    @GetMapping("/Tournaments/{TournamentId}")
    String oneTournament(@PathVariable("TournamentId") long id) {
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id)).toString();
    }

    @PutMapping("/Tournaments/{TournamentId}")
    String replaceTournament(@RequestBody Tournament newTournament,
            @PathVariable("TournamentId") long id) {
        return tournamentRepo.findById(id).map(tournament -> {
            tournament.setTournamentMatches(newTournament.getTournamentMatches());
            tournament.setTimeBetweenStages(newTournament.getTimeBetweenStages());
            tournament.setTournamentType(newTournament.getTournamentType());
            tournament.setCurrentMatch(newTournament.getCurrentMatch());
            tournament.setStartDate(newTournament.getStartDate());
            tournament.setFinished(newTournament.getFinished());
            tournament.setEndDate(newTournament.getEndDate());
            tournament.setName(newTournament.getName());
            tournament.setOpen(newTournament.getOpen());
            return tournamentRepo.save(tournament).toString();
        }).orElseGet(() -> {
            newTournament.setId(id);
            return tournamentRepo.save(newTournament).toString();
        });
    }

    @DeleteMapping("/Tournaments/{TournamentId}")
    void deleteTournament(@PathVariable("TournamentId") long id) {
        tournamentRepo.deleteById(id);
    }

    @GetMapping("/Tournaments/addParticipant/{TournamentId}/{ParticipantId}")
    String addParticipant(@PathVariable("TournamentId") long tournamentId,
            @PathVariable("ParticipantId") long participantId) {
        Optional<Tournament> temp = tournamentRepo.findById(tournamentId);
        temp.ifPresentOrElse(t -> {
            participantRepo.findById(participantId).ifPresentOrElse(p -> {
                t.addParticipant(p);
                participantRepo.save(p);
                tournamentRepo.save(t);
            }, () -> {
                throw new ParticipantNotFoundException(participantId);
            });
        }, () -> {
            throw new TournamentNotFoundException(tournamentId);
        });
        return "Successful";
    }
}
