package com.SWE.project.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SWE.project.Classes.Participant;

public interface ParticipantRepo extends JpaRepository<Participant, Long> {

}
