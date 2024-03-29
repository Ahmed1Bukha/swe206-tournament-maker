package com.SWE.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TournamentNotFoundException extends RuntimeException {
    public TournamentNotFoundException(long id) {
        super("Could not find tournament " + id);
    }
}

@ControllerAdvice
class TournamentNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String TournamentNotFoundHandler(Exception ex) {
        return ex.getMessage();
    }
}