package com.SWE.project.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SWE.project.Classes.Student;
import com.SWE.project.Repositories.TeamRepo;

@RestController
public class TeamController {

    @Autowired
    private final TeamRepo repo;

    public TeamController(TeamRepo repo) {
        this.repo = repo;
    }

}
