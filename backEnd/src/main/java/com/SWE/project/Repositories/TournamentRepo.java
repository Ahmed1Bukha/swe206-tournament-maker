package com.SWE.project.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SWE.project.Classes.Tournament;

public interface TournamentRepo extends JpaRepository<Tournament, String> {

}