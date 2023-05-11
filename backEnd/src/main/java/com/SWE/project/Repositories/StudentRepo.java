package com.SWE.project.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SWE.project.Classes.Student;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {
    public Optional<Student> findByStudentId(Long studentId);
}