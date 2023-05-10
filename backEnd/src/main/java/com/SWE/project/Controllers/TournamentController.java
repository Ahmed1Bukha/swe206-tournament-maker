package com.SWE.project.Controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SWE.project.Classes.*;
import com.SWE.project.Exceptions.*;
import com.SWE.project.Repositories.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
public class TournamentController {

    @Autowired
    private final TournamentRepo tournamentRepo;

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
        this.studentRepo = studentRepo;
        this.teamRepo = teamRepo;
        this.participantRepo = participantRepo;
    }

    @GetMapping("/Tournaments")
    List<Tournament> allTournaments() {
        return tournamentRepo.findAll();
    }

    @GetMapping("/Tournaments/getMatches/{TournamentId}")
    List<Match> getMatches(@PathVariable("TournamentId") Long id) {
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id))
                .getTournamentMatches();
    }

    @GetMapping("/Tournaments/getParticipants/{TournamentId}")
    Set<Participant> getParticipants(@PathVariable("TournamentId") long id)
            throws TournamentNotFoundException, JsonMappingException,
            JsonProcessingException {
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id)).getParticipants();
    }

    @PostMapping(value = "/RoundRobinTournaments")
    RoundRobinTournament newRoundRobinTournament(@RequestBody RoundRobinTournament newTournament) {
        return tournamentRepo.save(newTournament);
    }

    @PostMapping("/EliminationTournaments")
    EliminationTournament newEliminationTournament(@RequestBody EliminationTournament newTournament) {
        return tournamentRepo.save(newTournament);
    }

    @GetMapping("/Tournaments/{TournamentId}")
    Tournament oneTournament(@PathVariable("TournamentId") long id)
            throws TournamentNotFoundException {
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id));
    }

    @PutMapping("/Tournaments/{TournamentId}")
    Tournament replaceTournament(@RequestBody Tournament newTournament,
            @PathVariable("TournamentId") long id)
            throws TournamentNotFoundException {
        return tournamentRepo.findById(id).map(tournament -> {
            // tournament.setTournamentMatches(newTournament.getTournamentMatches());
            tournament.setTimeBetweenStages(newTournament.getTimeBetweenStages());
            tournament.setTournamentType(newTournament.getTournamentType());
            tournament.setCurrentMatch(newTournament.getCurrentMatch());
            tournament.setStartDate(newTournament.getStartDate());
            tournament.setFinished(newTournament.getFinished());
            tournament.setEndDate(newTournament.getEndDate());
            tournament.setName(newTournament.getName());
            tournament.setOpen(newTournament.getOpen());
            try {
                return tournamentRepo.save(tournament);
            } catch (Exception e) {
                return null;
            }
        }).orElseGet(() -> {
            newTournament.setId(id);
            try {
                return tournamentRepo.save(newTournament);
            } catch (Exception e) {
                return null;
            }
        });
    }

    @DeleteMapping("/Tournaments/{TournamentId}")
    void deleteTournament(@PathVariable("TournamentId") long id) {
        tournamentRepo.deleteById(id);
    }

    @PostMapping("/Tournaments/addParticipant")
    Tournament addParticipant(@RequestBody Map<String, Long> ids) {
        long tournamentId = ids.get("tournamentId");
        long participantId = ids.get("participantId");
        Optional<Tournament> temp = tournamentRepo.findById(tournamentId);
        temp.ifPresentOrElse(t -> {
            participantRepo.findById(participantId).ifPresentOrElse(p -> {
                if (!(t.getOpen()))
                    throw new TournamentRegistrationClosedException(tournamentId);
                // Check if full
                t.addParticipant(p);
                tournamentRepo.save(t);
                participantRepo.save(p);
            }, () -> {
                throw new ParticipantNotFoundException(participantId);
            });
        }, () -> {
            throw new TournamentNotFoundException(tournamentId);
        });
        return temp.get();
    }
}
