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
import com.SWE.project.Classes.Views;
import com.SWE.project.Exceptions.StudentNotFoundException;
import com.SWE.project.Repositories.ParticipantRepo;
import com.SWE.project.Repositories.StudentRepo;
import com.SWE.project.Repositories.TeamRepo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
public class ParticipantController {

    @Autowired
    private final StudentRepo studentRepo;

    @Autowired
    private final TeamRepo teamRepo;

    @Autowired
    private final ParticipantRepo participantRepo;

    private final ObjectMapper om;

    public ParticipantController(StudentRepo studentRepo, TeamRepo teamRepo, ParticipantRepo participantRepo) {
        this.studentRepo = studentRepo;
        this.teamRepo = teamRepo;
        this.participantRepo = participantRepo;
        this.om = new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    }

    @GetMapping("/students")
    List<Student> allStudents() throws JsonMappingException, JsonProcessingException {
        // return om.readValue(om.writerWithView(Views.Public.class)
        // .writeValueAsString(studentRepo.findAll()), new
        // TypeReference<List<Student>>() {
        // });
        return studentRepo.findAll();
    }

    @PostMapping("/students")
    Student newStudent(@RequestBody Student newStudent) throws JsonMappingException, JsonProcessingException {
        // return om.readValue(om.writerWithView(Views.Public.class)
        // .writeValueAsString(studentRepo.save(newStudent)), Student.class);
        return studentRepo.save(newStudent);
    }

    @GetMapping("/students/{id}")
    Student oneStudent(@PathVariable Long id) {
        return studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
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
