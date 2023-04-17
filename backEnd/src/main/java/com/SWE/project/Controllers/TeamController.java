package com.SWE.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.SWE.project.Classes.Team;
import com.SWE.project.Exceptions.TeamNotFoundException;
import com.SWE.project.Repositories.TeamRepo;

@RestController
public class TeamController {

    @Autowired
    private final TeamRepo repo;

    public TeamController(TeamRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/Teams/{TeamId}")
    Team oneTournament(@PathVariable long id) {
        return repo.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }

}
