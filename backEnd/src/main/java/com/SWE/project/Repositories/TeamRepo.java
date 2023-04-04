package com.SWE.project.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SWE.project.Classes.Team;

public interface TeamRepo extends JpaRepository<Team, Long> {

}
