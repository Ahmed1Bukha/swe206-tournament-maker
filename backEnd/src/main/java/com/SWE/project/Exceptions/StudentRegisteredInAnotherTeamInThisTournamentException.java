package com.SWE.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class StudentRegisteredInAnotherTeamInThisTournamentException extends RuntimeException {
    public StudentRegisteredInAnotherTeamInThisTournamentException(Long studentId, Long teamId, Long tournamentId) {
        super(String.format("Student %d is already registered with team %d in tournament %d", studentId, teamId,
                tournamentId));
    }
}

@ControllerAdvice
class StudentRegisteredInAnotherTeamInThisTournamentAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String StudentRegisteredInAnotherTeamInThisTournamentHandler(Exception ex) {
        return ex.getMessage();
    }
}