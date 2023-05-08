package com.SWE.project.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SWE.project.Classes.Tournament;

public interface TournamentRepo extends JpaRepository<Tournament, Long> {
    // List<Tournament> findByName(String name);
}