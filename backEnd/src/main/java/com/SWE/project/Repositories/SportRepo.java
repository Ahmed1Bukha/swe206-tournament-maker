package com.SWE.project.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SWE.project.Classes.Sport;
import java.util.List;

public interface SportRepo extends JpaRepository<Sport, Long> {
    // List<Sport> findByName(String name);
}
