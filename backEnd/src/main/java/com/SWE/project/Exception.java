package com.SWE.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Could not find student " + id);
    }
}

@ControllerAdvice
class StudentNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String StudentNotFoundHandler(Exception ex) {
        return ex.getMessage();
    }
}

class TournamentNotFoundException extends RuntimeException {
    public TournamentNotFoundException(String name) {
        super("Could not find tournament " + name);
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