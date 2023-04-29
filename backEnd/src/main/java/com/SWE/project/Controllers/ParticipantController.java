package com.SWE.project.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SWE.project.Classes.Student;
import com.SWE.project.Exceptions.StudentNotFoundException;
import com.SWE.project.Repositories.ParticipantRepo;
import com.SWE.project.Repositories.StudentRepo;
import com.SWE.project.Repositories.TeamRepo;

@RestController
public class ParticipantController {

    @Autowired
    private final StudentRepo studentRepo;

    @Autowired
    private final TeamRepo teamRepo;

    @Autowired
    private final ParticipantRepo participantRepo;

    public ParticipantController(StudentRepo studentRepo, TeamRepo teamRepo, ParticipantRepo participantRepo) {
        this.studentRepo = studentRepo;
        this.teamRepo = teamRepo;
        this.participantRepo = participantRepo;
    }

    @GetMapping("/students")
    List<Student> allStudents() {
        return studentRepo.findAll();
    }

    @PostMapping("/students")
    String newStudent(@RequestBody Student newStudent) {
        return studentRepo.save(newStudent).toString();
    }

    @GetMapping("/students/{id}")
    String oneStudent(@PathVariable Long id) {
        return studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException(id)).toString();
    }

    @PutMapping("/students/{id}")
    String replaceStudent(@RequestBody Student newStudent, @PathVariable Long id) {
        return studentRepo.findById(id).map(student -> {
            student.setGoalsRecieved(newStudent.getGoalsRecieved());
            student.setTournaments(newStudent.getTournaments());
            student.setGoalsMade(newStudent.getGoalsMade());
            student.setMatches(newStudent.getMatches());
            student.setPoints(newStudent.getPoints());
            student.setWins(newStudent.getWins());
            student.setName(newStudent.getName());
            student.setId(newStudent.getId());
            return studentRepo.save(student).toString();
        }).orElseGet(() -> {
            newStudent.setId(id);
            return studentRepo.save(newStudent).toString();
        });
    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id) {
        studentRepo.deleteById(id);
    }
}
