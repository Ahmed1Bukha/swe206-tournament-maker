package com.SWE.project.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SWE.project.Classes.Tournament;
import com.SWE.project.Exceptions.TournamentNotFoundException;
import com.SWE.project.Repositories.TournamentRepo;

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

    @PostMapping("/Tournaments")
    Tournament newTournament(@RequestBody Tournament newTournament) {
        return repo.save(newTournament);
    }

    @GetMapping("/Tournaments/{id}")
    Tournament oneTournament(@PathVariable String name) {
        return repo.findById(name).orElseThrow(() -> new TournamentNotFoundException(name));
    }

    @PutMapping("/Tournaments/{id}")
    Tournament replaceTournament(@RequestBody Tournament newTournament, @PathVariable String name) {
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

    @DeleteMapping("/Tournaments/{id}")
    void deleteTournament(@PathVariable String name) {
        repo.deleteById(name);
    }
}
