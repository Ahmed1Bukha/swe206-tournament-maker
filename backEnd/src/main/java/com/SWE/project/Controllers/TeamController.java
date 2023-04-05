package com.SWE.project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.SWE.project.Repositories.TeamRepo;

@RestController
public class TeamController {

    @Autowired
    private final TeamRepo repo;

    public TeamController(TeamRepo repo) {
        this.repo = repo;
    }

}
