package com.SWE.project.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SWE.project.Classes.*;
import com.SWE.project.Enums.TOURNAMENT_TYPES;
import com.SWE.project.Exceptions.*;
import com.SWE.project.Repositories.*;

import ch.qos.logback.core.net.SyslogOutputStream;

@RestController
public class TournamentController {

    @Autowired
    private final TournamentRepo tournamentRepo;

    @Autowired
    private final StudentRepo studentRepo;

    @Autowired
    private final TeamRepo teamRepo;

    @Autowired
    public final ParticipantRepo participantRepo;

    @Autowired
    private final MatchRepo matchRepo;

    @Autowired
    private final SportRepo sportRepo;

    public TournamentController(TournamentRepo tournamentRepo, RoundRobinTournamentRepo roundRobinRepo,
            EliminationTournamentRepo eliminationRepo, StudentRepo studentRepo, TeamRepo teamRepo,
            ParticipantRepo participantRepo, MatchRepo matchRepo, SportRepo sportRepo) {
        this.tournamentRepo = tournamentRepo;
        this.studentRepo = studentRepo;
        this.teamRepo = teamRepo;
        this.participantRepo = participantRepo;
        this.matchRepo = matchRepo;
        this.sportRepo = sportRepo;
    }

    @GetMapping("/Tournaments")
    List<Tournament> allTournaments() {
        try {
            List<Tournament> tournamens = tournamentRepo.findAll();
            return tournamens;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/Tournaments/getMatches/{tournamentId}")
    List<Match> getMatches(@PathVariable("tournamentId") Long id) {
        Tournament t = tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id));
        tranform(t);
        if (t instanceof EliminationTournament) {
            EliminationTournament et = (EliminationTournament) t;
            List<Match> result = new ArrayList<Match>();
            for (Match m : et.getTournamentMatches()) {
                System.out.println("hhhh");
                System.out.println(m.getId());
                result.add(m);
                System.out.println("hhhhhhhhhhhhhhhhhhh");
            }

            return result;
        }
        return t.getTournamentMatches();
    }

    @GetMapping("/Tournaments/getParticipants/{tournamentId}")
    Set<Participant> getParticipants(@PathVariable("tournamentId") long id)
            throws TournamentNotFoundException {
        return tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id)).getParticipants();
    }

    @PostMapping(value = "/RoundRobinTournaments")
    RoundRobinTournament newRoundRobinTournament(@RequestBody Map body) {
        String name = (String) body.get("name");
        int participantCount = (int) body.get("participantCount");
        int studentsPerTeam = (int) body.get("studentsPerTeam");
        String[] s = ((String) body.get("startDate")).split("-");
        int[] startDate = new int[] { Integer.parseInt(s[0]) - 1900, Integer.parseInt(s[1]) - 1,
                Integer.parseInt(s[2]) - 1 };
        s = ((String) body.get("endDate")).split("-");
        int[] endDate = new int[] { Integer.parseInt(s[0]) - 1900, Integer.parseInt(s[1]) - 1,
                Integer.parseInt(s[2]) - 1 };
        double timeBetweenStages = (double) body.get("timeBetweenStages");
        String tournamentType = (String) body.get("tournamentType");
        String sport = (String) body.get("sport");
        RoundRobinTournament newTournament = new RoundRobinTournament(name, participantCount, studentsPerTeam,
                startDate, endDate, timeBetweenStages, tournamentType, sport);
        return tournamentRepo.save(newTournament);
    }

    @PostMapping("/EliminationTournaments")
    EliminationTournament newEliminationTournament(@RequestBody Map body) {
        String name = (String) body.get("name");
        int participantCount = (int) body.get("participantCount");
        int studentsPerTeam = (int) body.get("studentsPerTeam");
        String[] s = ((String) body.get("startDate")).split("-");
        int[] startDate = new int[] { Integer.parseInt(s[0]) - 1900, Integer.parseInt(s[1]) - 1,
                Integer.parseInt(s[2]) - 1 };
        s = ((String) body.get("endDate")).split("-");
        int[] endDate = new int[] { Integer.parseInt(s[0]) - 1900, Integer.parseInt(s[1]) - 1,
                Integer.parseInt(s[2]) - 1 };
        double timeBetweenStages = (double) body.get("timeBetweenStages");
        String tournamentType = (String) body.get("tournamentType");
        String sport = (String) body.get("sport");
        EliminationTournament newTournament = new EliminationTournament(name, participantCount, studentsPerTeam,
                startDate, endDate, timeBetweenStages, tournamentType, sport);
        return tournamentRepo.save(newTournament);
    }

    @GetMapping("/Tournaments/forceCloseTournament/{id}")
    void forceClose(@PathVariable Long id) {
        Tournament t = tournamentRepo.findById(id).orElseThrow(() -> new TournamentNotFoundException(id));

    }

    @GetMapping("/RoundRobinTournaments/getMatches/{tournamentId}")
    Map roundRobinJsonFormat(@PathVariable("tournamentId") Long id) {
        // Tournament t =
    }

    @GetMapping("/EliminationTournaments/getMatches/{tournamentId}")
    Map<String, List<Map>> elimMatchesJsonFormat(@PathVariable("tournamentId") Long id) {
        EliminationTournament t = (EliminationTournament) tournamentRepo.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));
        tranform(t);
        System.out.println(t.getTournamentMatches());
        if (t.getOpen())
            throw new TournamentRegistrationStillOpenException(id);

        int participantCount = t.getParticipantCount();
        int numOfMatches = 0;
        int i = 0;

        while (participantCount != 1) {
            participantCount = (int) Math.ceil(participantCount / 2.0);
            numOfMatches += participantCount;
        }

        Map<Integer, Match> matches = new HashMap<Integer, Match>();

        for (String preset : t.getAllRounds()) {
            System.out.println("Preset: " + preset);
            String[] set = preset.split(".");
            if (set.length == 0) {
                String[] x = { preset };
                set = x;
            }
            for (String m : set) {
                String[] matchArray = m.split(",");
                System.out.println("M:" + m);
                if (matchArray[2].equals("Dummy")) {
                    matches.put(numOfMatches - i, new Match(
                            new Participant[] { participantRepo.findById(Long.parseLong(matchArray[0]))
                                    .orElseThrow(() -> new StudentNotFoundException(Long.parseLong(matchArray[0]))),
                                    null },
                            true));
                } else {
                    matches.put(numOfMatches - i, new Match(
                            new Participant[] {
                                    participantRepo.findById(Long.parseLong(matchArray[0]))
                                            .orElseThrow(
                                                    () -> new StudentNotFoundException(Long.parseLong(matchArray[0]))),
                                    participantRepo.findById(Long.parseLong(matchArray[2])).orElseThrow(
                                            () -> new StudentNotFoundException(Long.parseLong(matchArray[2]))) },
                            Integer.parseInt(matchArray[1]), Integer.parseInt(matchArray[3]),
                            !(matchArray[1].equals("-1") && matchArray[3].equals("-1")), false));
                }
                i++;
            }
        }
        System.out.println(matches + "nnn");
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
    List<String> getSports() {
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
            // tournament.setCurrentMatch(newTournament.getCurrentMatch());
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

    void tranform(Tournament t) {
        if (t.getOpen())
            return;
        ArrayList<String> array = t.generatedMatches;
        ArrayList<Match> matches = new ArrayList<>();
        for (String i : array) {
            String[] matchArray = i.split(",");
            if (matchArray[2].equals("Dummy")) {
                matches.add(new Match(
                        new Participant[] { participantRepo.findById(Long.parseLong(matchArray[0]))
                                .orElseThrow(() -> new StudentNotFoundException(Long.parseLong(matchArray[0]))), null },
                        true));
            } else {
                matches.add(new Match(
                        new Participant[] {
                                participantRepo.findById(Long.parseLong(matchArray[0]))
                                        .orElseThrow(() -> new StudentNotFoundException(Long.parseLong(matchArray[0]))),
                                participantRepo.findById(Long.parseLong(matchArray[2])).orElseThrow(
                                        () -> new StudentNotFoundException(Long.parseLong(matchArray[2]))) },
                        Integer.parseInt(matchArray[1]), Integer.parseInt(matchArray[3]),
                        !(matchArray[1].equals("-1") && matchArray[3].equals("-1")), false));
            }
        }
        t.startMatches(matches);
    }

    @PostMapping("/Tournaments/addParticipant")
    Tournament addParticipant(@RequestBody Map<String, Long> ids) {
        long tournamentId = ids.get("tournamentId");
        long studentId = ids.get("participantId");
        Optional<Tournament> temp = tournamentRepo.findById(tournamentId);
        temp.ifPresentOrElse(t -> {
            tranform(t);
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
                    throw new StudentAlreadyRegisteredInThisTournamentException(t.getId(),
                            studentId);

                t.addParticipant(p);
                t.storeMatches();
                EliminationTournament tt = (EliminationTournament) t;
                tt.getTournamentMatches().clear();
                tt.setCurrentMatch(null);
                // tt.getAllRounds().clear();
                studentRepo.save(p);

                // tournamentRepo.save(t);
            }, () -> {
                throw new ParticipantNotFoundException(studentId);
            });
        }, () -> {
            throw new TournamentNotFoundException(tournamentId);
        });
        System.out.println("ahh5");
        return temp.get();
    }

    // @PostMapping("/Match")
    // public Match m() {
    // Match[] matches = new Match[];
    // for
    // return matchRepo.save(match);
    // }
}
