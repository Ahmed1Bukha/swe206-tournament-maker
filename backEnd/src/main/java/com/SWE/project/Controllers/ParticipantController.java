package com.SWE.project.Controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SWE.project.Classes.*;
import com.SWE.project.Enums.GAME_TYPE;
import com.SWE.project.Exceptions.*;
import com.SWE.project.Repositories.ParticipantRepo;
import com.SWE.project.Repositories.StudentRepo;
import com.SWE.project.Repositories.TeamRepo;
import com.SWE.project.Repositories.TournamentRepo;

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
    List<Student> allStudents() {
        return studentRepo.findAll();
    }

    @GetMapping("/teams")
    List<Team> allTeams() {
        return teamRepo.findAll();
    }

    @GetMapping("/participants")
    List<Participant> allParticipants() {
        return participantRepo.findAll();
    }

    @PostMapping("/students")
    Student newStudent(@RequestBody Student newStudent) {
        if (studentRepo.findByStudentId(newStudent.getStudentId()).isPresent())
            return newStudent;

        return studentRepo.save(newStudent);
    }

    @PostMapping("/teams")
    Team newTeam(@RequestBody Map<String, Object> body) {
        Long tournament_id = Long.parseLong((body.get("tournament_id")).toString());
        String name = (String) body.get("name");
        List<Long> students_ids = new ArrayList<Long>();
        ((List<Integer>) body.get("team_members")).forEach(e -> {
            students_ids.add(Long.parseLong(Integer.toString(e)));
        });

        GAME_TYPE gameType = ((String) body.get("gameType")).equals("RoundRobin") ? GAME_TYPE.RoundRobin
                : GAME_TYPE.Elimination;
        Tournament t = tournamentRepo.findById(tournament_id)
                .orElseThrow(() -> new TournamentNotFoundException(tournament_id));
        System.out.println("Debug 1");
        List<Team> ps = t.getParticipants().stream().filter(p -> p instanceof Team).map(p -> (Team) p)
                .collect(Collectors.toList());
        System.out.println("Debug 2");
        for (Team team : ps) {
            System.out.println("Debug 2.1");
            for (Student alreadyRegisteredStudent : team.getTeam_members()) {
                System.out.println("Debug 2.2");
                for (Long toBeRegisteredStudentId : students_ids) {
                    System.out.println("Debug 2.3");
                    if (toBeRegisteredStudentId.equals(alreadyRegisteredStudent.getStudentId()))
                        throw new StudentRegisteredInAnotherTeamInThisTournamentException(toBeRegisteredStudentId,
                                team.getId(), t.getId());
                    System.out.println("Debug 2.4");
                }
            }
        } // Checks if any members of the team are already registered in a different team.
        System.out.println("Debug 3");

        Set<Student> team_members = new HashSet<>();

        System.out.println("Debug 4");

        for (Long id : students_ids) {
            team_members.add(studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException(id)));
        } // Once this is done, all student ids are valid and we can create the team

        System.out.println("Debug 5");

        Team team = new Team(name, team_members, gameType, t);

        System.out.println("Debug 6");

        t.addParticipant(team);

        System.out.println("Debug 7");

        for (Student s : team_members) {
            s.getTeams().add(team);
        }

        System.out.println("Debug 7.5");
        Team p = participantRepo.save(team);
        System.out.println("Debug 8");

        participantRepo.saveAll(team_members);
        System.out.println("Debug 9");

        tournamentRepo.save(t);

        System.out.println("Debug 10");

        return p;
    }

    @GetMapping("/students/{id}")
    Participant oneStudent(@PathVariable Long id) {
        return studentRepo.findByStudentId(id).orElseThrow(() -> new ParticipantNotFoundException(id));
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
