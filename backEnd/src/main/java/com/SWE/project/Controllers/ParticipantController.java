package com.SWE.project.Controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SWE.project.Classes.*;
import com.SWE.project.Enums.GAME_TYPE;
import com.SWE.project.Exceptions.*;
import com.SWE.project.Repositories.ParticipantRepo;
import com.SWE.project.Repositories.StudentRepo;
import com.SWE.project.Repositories.TeamRepo;
import com.SWE.project.Repositories.TournamentRepo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class ParticipantController {

    @Autowired
    private final TournamentRepo tournamentRepo;

    @Autowired
    private final StudentRepo studentRepo;

    @Autowired
    private final TeamRepo teamRepo;

    @Autowired
    private final ParticipantRepo participantRepo;

    public ParticipantController(StudentRepo studentRepo, TeamRepo teamRepo, ParticipantRepo participantRepo,
            TournamentRepo tournamentRepo) {
        this.studentRepo = studentRepo;
        this.teamRepo = teamRepo;
        this.participantRepo = participantRepo;
        this.tournamentRepo = tournamentRepo;
    }

    @GetMapping("/students")
    List<Student> allStudents() throws JsonMappingException, JsonProcessingException {

        return studentRepo.findAll();
    }

    @GetMapping("/teams")
    List<Team> allTeams() throws JsonMappingException, JsonProcessingException {
        return teamRepo.findAll();
    }

    @GetMapping("/students")
    List<Participant> allParticipants() throws JsonMappingException, JsonProcessingException {
        return participantRepo.findAll();
    }

    @PostMapping("/students")
    Student newStudent(@RequestBody Student newStudent) throws JsonMappingException, JsonProcessingException {
        return studentRepo.save(newStudent);
    }

    @PostMapping("/Teams")
    Team newTeam(@RequestBody Map<String, Object> body) throws JsonMappingException, JsonProcessingException {
        Tournament t = tournamentRepo.findById(((Tournament) body.get("tournament")).getId())
                .orElseThrow(() -> new TournamentAlreadyExistsException(((Tournament) body.get("tournament")).getId()));

        Team[] ps = (Team[]) t.getParticipants().toArray();

        for (Team team : ps) {
            for (Student alreadyRegisteredStudent : team.getTeam_members()) {
                for (Long toBeRegisteredStudentId : (List<Long>) body.get("team_members")) {
                    if (toBeRegisteredStudentId.equals(alreadyRegisteredStudent.getStudentId()))
                        throw new StudentRegisteredInAnotherTeamInThisTournamentException(toBeRegisteredStudentId,
                                team.getId(), ((Tournament) body.get("tournament")).getId());
                }
            }
        } // Checks if any members of the team are already registered in a different team.

        Set<Student> team_members = new HashSet<>();

        for (Long id : (List<Long>) body.get("team_members")) {
            team_members.add(studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException(id)));
        } // Once this is done, all student ids are valid and we can create the team

        return participantRepo.save(new Team(
                (String) body.get("name"),
                team_members,
                (String) body.get("gameType") == "RoundRobin" ? GAME_TYPE.RoundRobin : GAME_TYPE.Elimination,
                ((Tournament) body.get("tournament"))));
    }

    @GetMapping("/participants/{id}")
    Participant oneParticipant(@PathVariable Long id) {
        return participantRepo.findById(id).orElseThrow(() -> new ParticipantNotFoundException(id));
    }

    // @PutMapping("/students/{id}")
    // String replaceStudent(@RequestBody Student newStudent, @PathVariable Long id)
    // {
    // return studentRepo.findById(id).map(student -> {
    // student.setGoalsRecieved(newStudent.getGoalsRecieved());
    // student.setTournaments(newStudent.getTournaments());
    // student.setGoalsMade(newStudent.getGoalsMade());
    // // student.setMatches(newStudent.getMatches());
    // student.setPoints(newStudent.getPoints());
    // student.setWins(newStudent.getWins());
    // student.setName(newStudent.getName());
    // student.setId(newStudent.getId());
    // return studentRepo.save(student).toString();
    // }).orElseGet(() -> {
    // newStudent.setId(id);
    // return studentRepo.save(newStudent).toString();
    // });
    // }

    @DeleteMapping("/participant/{id}")
    void deleteStudent(@PathVariable Long id) {
        participantRepo.deleteById(id);
    }
}
