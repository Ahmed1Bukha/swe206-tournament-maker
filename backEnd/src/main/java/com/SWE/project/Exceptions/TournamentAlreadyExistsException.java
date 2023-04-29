package com.SWE.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TournamentAlreadyExistsException extends RuntimeException {
    public TournamentAlreadyExistsException(long id) {
        super("Tournament " + id + " already exits");
    }
}

@ControllerAdvice
class TournamentAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String TournamentAlreadyExistsHandler(Exception ex) {
        return ex.getMessage();
    }
}