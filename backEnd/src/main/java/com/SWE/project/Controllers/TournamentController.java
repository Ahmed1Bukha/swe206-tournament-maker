package com.SWE.project.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private final StudentRepo studentRepo;

    @Autowired
    private final TeamRepo teamRepo;

    @Autowired
    private final ParticipantRepo participantRepo;

    @Autowired
    private final SportRepo sportRepo;

    public TournamentController(TournamentRepo tournamentRepo, RoundRobinTournamentRepo roundRobinRepo,
            EliminationTournamentRepo eliminationRepo, StudentRepo studentRepo, TeamRepo teamRepo,
            ParticipantRepo participantRepo, SportRepo sportRepo) {
        this.tournamentRepo = tournamentRepo;
        this.studentRepo = studentRepo;
        this.teamRepo = teamRepo;
        this.participantRepo = participantRepo;
        this.sportRepo = sportRepo;
    }

    @GetMapping("/Tournaments")
    List<Tournament> allTournaments() {
        return tournamentRepo.findAll();
    }

    @GetMapping("/Tournaments/getMatches/{tournamentId}")
    List<Match> getMatches(@PathVariable("tournamentId") Long id) {
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id))
                .getTournamentMatches();
    }

    @GetMapping("/Tournaments/getParticipants/{tournamentId}")
    Set<Participant> getParticipants(@PathVariable("tournamentId") long id)
            throws TournamentNotFoundException {
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

    @GetMapping("/EliminationTournaments/getMatches/{tournamentId}")
    Map<String, List<Map>> matchesJsonFormat(@PathVariable("tournamentId") Long id) {
        EliminationTournament t = (EliminationTournament) tournamentRepo.findById(id).orElseThrow(() -> {
            throw new TournamentNotFoundException(id);
        });

        int participantCount = t.getParticipantCount();
        int numOfMatches = 0;
        int i = 0;

        while (participantCount != 1) {
            participantCount = (int) Math.ceil(participantCount / 2.0);
            numOfMatches += participantCount;
        }

        Map<Integer, Match> matches = new HashMap<Integer, Match>();

        t.stopRegistration();
        t.generateMatches();
        t.start();

        for (Set<Match> set : t.getAllRounds()) {
            for (Match m : set) {
                matches.put(numOfMatches - i, m);
                i++;
            }
        }

        Map<String, List<Map>> result = new HashMap<>();
        result.put("nodes", new ArrayList<>());
        result.put("edges", new ArrayList<>());

        for (int j = numOfMatches; j > 0; j--) {
            Map map = new HashMap<>();
            map.put("id", j);
            map.put("label", matches.get(j) != null ? matches.get(j).toString() : "");
            result.get("nodes").add(map);
        }

        for (int j = 1; j < numOfMatches; j++) {
            if (j * 2 <= numOfMatches) {
                Map map = new HashMap<>();
                map.put("from", j);
                map.put("to", j * 2);
                result.get("edges").add(map);
                if (j * 2 + 1 <= numOfMatches) {
                    map = new HashMap<>();
                    map.put("from", j);
                    map.put("to", j * 2 + 1);
                    result.get("edges").add(map);
                }
            }
        }

        return result;
    }

    @GetMapping("/Tournaments/{tournamentId}")
    Tournament oneTournament(@PathVariable("tournamentId") long id)
            throws TournamentNotFoundException {
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id));
    }

    @GetMapping("/getSports")
    List<String> getSports() throws FileNotFoundException {
        List<String> res = new ArrayList<String>();
        sportRepo.findAll().forEach((sport -> {
            res.add(sport.getName());
        }));
        return res;
    }

    @PostMapping("/addSport")
    List<String> addNewSport(@RequestBody Map<String, String> body) throws IOException {
        sportRepo.save(new Sport((String) body.get("name")));
        List<String> res = new ArrayList<String>();
        sportRepo.findAll().forEach((s -> {
            res.add(s.getName());
        }));
        return res;
    }

    @PutMapping("/Tournaments/{tournamentId}")
    Tournament replaceTournament(@RequestBody Tournament newTournament,
            @PathVariable("tournamentId") long id)
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
        long studentId = ids.get("participantId");
        Optional<Tournament> temp = tournamentRepo.findById(tournamentId);
        temp.ifPresentOrElse(t -> {
            studentRepo.findByStudentId(studentId).ifPresentOrElse(p -> {
                if (!(t.getOpen()))
                    throw new TournamentRegistrationClosedException(tournamentId);

                boolean isAlreadyRegistered = false;
                for (Tournament tournament : p.getTournaments())
                    if (tournament.getId() == t.getId()) {
                        isAlreadyRegistered = true;
                        return;
                    }

                if (isAlreadyRegistered)
                    throw new StudentAlreadyRegisteredInThisTournamentException(t.getId(), studentId);

                t.addParticipant(p);
                tournamentRepo.save(t);
                studentRepo.save(p);
            }, () -> {
                throw new ParticipantNotFoundException(studentId);
            });
        }, () -> {
            throw new TournamentNotFoundException(tournamentId);
        });
        return temp.get();
    }
}
