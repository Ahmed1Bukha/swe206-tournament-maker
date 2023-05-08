package com.SWE.project.Controllers;

import java.util.*;
import java.util.stream.Stream;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.SWE.project.Classes.*;
import com.SWE.project.Exceptions.*;
import com.SWE.project.Repositories.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.micrometer.core.instrument.MeterRegistry.Config;

@RestController
public class TournamentController {

    @Autowired
    private final TournamentRepo tournamentRepo;

    // @Autowired
    // private final RoundRobinTournamentRepo roundRobinRepo;

    // @Autowired
    // private final EliminationTournamentRepo eliminationRepo;

    @Autowired
    private final StudentRepo studentRepo;

    @Autowired
    private final TeamRepo teamRepo;

    @Autowired
    private final ParticipantRepo participantRepo;

    private final ObjectMapper om;

    public TournamentController(TournamentRepo tournamentRepo, RoundRobinTournamentRepo roundRobinRepo,
            EliminationTournamentRepo eliminationRepo, StudentRepo studentRepo, TeamRepo teamRepo,
            ParticipantRepo participantRepo) {
        this.tournamentRepo = tournamentRepo;
        // this.roundRobinRepo = roundRobinRepo;
        // this.eliminationRepo = eliminationRepo;
        this.studentRepo = studentRepo;
        this.teamRepo = teamRepo;
        this.participantRepo = participantRepo;
        this.om = new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL, true);
    }

    @GetMapping("/Tournaments")
    List<Tournament> allTournaments() throws JsonProcessingException {
        // return om.readValue(om.writerWithView(Views.Public.class)
        // .writeValueAsString(
        // Stream.concat(eliminationRepo.findAll().stream(),
        // roundRobinRepo.findAll().stream()).toList()),
        // new TypeReference<List<Tournament>>() {
        // });
        return tournamentRepo.findAll();
        // return null;
    }

    @GetMapping("/Tournaments/getMatches/{TournamentId}")
    List<Match> getMatches(@PathVariable("TournamentId") Long id) throws JsonMappingException, JsonProcessingException {
        // return om.readValue(om.writerWithView(Views.Public.class)
        // .writeValueAsString(tournamentRepo.findById(id).orElseThrow(() -> new
        // TournamentNotFoundException(id))
        // .getTournamentMatches()),
        // new TypeReference<List<Match>>() {
        // });
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id))
                .getTournamentMatches();
    }

    @GetMapping("/Tournaments/getParticipants/{TournamentId}")
    Set<Participant> getParticipants(@PathVariable("TournamentId") long id)
            throws TournamentNotFoundException, JsonMappingException,
            JsonProcessingException {
        // return om.readValue(om.writerWithView(Views.Public.class)
        // .writeValueAsString(tournamentRepo.findById(id).orElseThrow(() -> new
        // TournamentNotFoundException(id))
        // .getParticipants()),
        // new TypeReference<List<Participant>>() {
        // });
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id)).getParticipants();
    }

    @PostMapping(value = "/RoundRobinTournaments")
    RoundRobinTournament newRoundRobinTournament(@RequestBody RoundRobinTournament newTournament)
            throws JsonMappingException, JsonProcessingException {
        // System.out.println(newTournament);
        // return om.readValue(om.writerWithView(Views.Public.class)
        // .writeValueAsString(roundRobinRepo.save(newTournament)),
        // RoundRobinTournament.class);
        // System.out.println(newTournament.toString());
        return tournamentRepo.save(newTournament);
    }

    @PostMapping("/EliminationTournaments")
    EliminationTournament newEliminationTournament(@RequestBody EliminationTournament newTournament)
            throws JsonMappingException, JsonProcessingException {
        // return om.readValue(om.writerWithView(Views.Public.class)
        // .writeValueAsString(eliminationRepo.save(newTournament)),
        // EliminationTournament.class);
        return tournamentRepo.save(newTournament);
    }

    @GetMapping("/Tournaments/{TournamentId}")
    Tournament oneTournament(@PathVariable("TournamentId") long id)
            throws JsonMappingException, JsonProcessingException,
            TournamentNotFoundException {
        // return om.readValue(om.writerWithView(Views.Public.class)
        // .writeValueAsString(tournamentRepo.findById(id).orElseThrow(() -> new
        // TournamentNotFoundException(id))),
        // Tournament.class);
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id));
    }

    @PutMapping("/Tournaments/{TournamentId}")
    Tournament replaceTournament(@RequestBody Tournament newTournament,
            @PathVariable("TournamentId") long id)
            throws TournamentNotFoundException, JsonMappingException,
            JsonProcessingException {
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
            try {
                // return om.readValue(om.writerWithView(Views.Public.class)
                // .writeValueAsString(tournamentRepo.save(tournament)),
                // Tournament.class);
                return tournamentRepo.save(tournament);
            } catch (Exception e) {
                return null;
            }
        }).orElseGet(() -> {
            newTournament.setId(id);
            try {
                // return om.readValue(om.writerWithView(Views.Public.class)
                // .writeValueAsString(tournamentRepo.save(newTournament)),
                // Tournament.class);
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
    Tournament addParticipant(@RequestBody Map<String, Long> ids) throws JsonMappingException, JsonProcessingException {
        long tournamentId = ids.get("tournamentId");
        long participantId = ids.get("participantId");
        Optional<Tournament> temp = tournamentRepo.findById(tournamentId);
        temp.ifPresentOrElse(t -> {
            participantRepo.findById(participantId).ifPresentOrElse(p -> {
                if (!(t.getOpen()))
                    throw new TournamentRegistrationClosedException(tournamentId);
                // Check if full
                t.addParticipant(p);
                participantRepo.save(p);
                tournamentRepo.save(t);
            }, () -> {
                throw new ParticipantNotFoundException(participantId);
            });
        }, () -> {
            throw new TournamentNotFoundException(tournamentId);
        });
        return temp.get();
    }
}
