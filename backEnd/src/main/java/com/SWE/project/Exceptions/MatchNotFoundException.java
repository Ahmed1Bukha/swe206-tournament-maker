package com.SWE.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(Long id) {
        super("Could not find match " + id);
    }
}

@ControllerAdvice
class MatchNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String MatchNotFoundHandler(Exception ex) {
        return ex.getMessage();
    }
}