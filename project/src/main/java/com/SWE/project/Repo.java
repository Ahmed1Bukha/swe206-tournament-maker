package com.SWE.project;

import org.springframework.data.jpa.repository.JpaRepository;

interface StudentRepo extends JpaRepository<Student, Long> {

}

interface TournamentRepo extends JpaRepository<Tournament, String> {

}
