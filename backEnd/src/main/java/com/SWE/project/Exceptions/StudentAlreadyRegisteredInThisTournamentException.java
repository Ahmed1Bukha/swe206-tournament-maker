package com.SWE.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class StudentAlreadyRegisteredInThisTournamentException extends RuntimeException {
    public StudentAlreadyRegisteredInThisTournamentException(Long tournamentId, Long studentId) {
        super(String.format("Student %d already registered in tournament %d", studentId, tournamentId));
    }
}

@ControllerAdvice
class StudentAlreadyRegisteredInThisTournamentAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String StudentAlreadyRegisteredInThisTournamentHandler(Exception ex) {
        return ex.getMessage();
    }
}
