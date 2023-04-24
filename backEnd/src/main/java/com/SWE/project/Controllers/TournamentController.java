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
    private final TournamentRepo repo;

    public TournamentController(TournamentRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/Tournaments")
    List<Tournament> allTournaments() {
        return repo.findAll();
    }

    @GetMapping("/Tournaments/getMatches/{TournamentName}")
    List<Match> getMatches(@PathVariable String name) {
        Optional<Tournament> temp = repo.findById(name);
        if (temp.isEmpty())
            throw new TournamentNotFoundException(name);

        return temp.get().getTournamentMatches();
    }

    @GetMapping("/Tournaments/getParticipants/{TournamentName}")
    Set<Participant> getStudents(@PathVariable String name) {
        Optional<Tournament> temp = repo.findById(name);
        if (temp.isEmpty())
            throw new TournamentNotFoundException(name);

        return temp.get().getParticipants();
    }

    @PostMapping("/Tournaments")
    Tournament newTournament(@RequestBody Tournament newTournament) {
        return repo.save(newTournament);
    }

    @GetMapping("/Tournaments/{TournamentName}")
    Tournament oneTournament(@PathVariable String name) {
        return repo.findById(name).orElseThrow(() -> new TournamentNotFoundException(name));
    }

    @PutMapping("/Tournaments/{TournamentName}")
    Tournament replaceTournament(@RequestBody Tournament newTournament,
            @PathVariable String name) {
        return repo.findById(name).map(Tournament -> {
            Tournament.setName(newTournament.getName());
            Tournament.setStartDate(newTournament.getStartDate());
            Tournament.setEndDate(newTournament.getEndDate());
            Tournament.setTimeBetweenStages(newTournament.getTimeBetweenStages());
            Tournament.setTournamentType(newTournament.getTournamentType());
            return repo.save(Tournament);
        }).orElseGet(() -> {
            newTournament.setName(name);
            return repo.save(newTournament);
        });
    }

    @DeleteMapping("/Tournaments/{TournamentName}")
    void deleteTournament(@PathVariable String name) {
        repo.deleteById(name);
    }
}
