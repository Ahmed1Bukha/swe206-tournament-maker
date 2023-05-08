package com.SWE.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TournamentRegistrationClosedException extends RuntimeException {
    public TournamentRegistrationClosedException(long id) {
        super("Tournament " + id + " is closed");
    }
}

@ControllerAdvice
class TournamentRegistrationClosedAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String TournamentRegistrationClosedHandler(Exception ex) {
        return ex.getMessage();
    }
}