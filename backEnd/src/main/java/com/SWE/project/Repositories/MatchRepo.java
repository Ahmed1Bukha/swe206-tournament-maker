package com.SWE.project.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SWE.project.Classes.Match;

public interface MatchRepo extends JpaRepository<Match, Long> {

}
