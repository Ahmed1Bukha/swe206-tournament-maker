package com.SWE.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TournamentRegistrationStillOpenException extends RuntimeException {
    public TournamentRegistrationStillOpenException(Long id) {
        super(String.format("cannot view matches of tournament %d because it is still open", id));
    }
}

@ControllerAdvice
class TournamentRegistrationStillOpenAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String TournamentRegistrationStillOpenHandler(Exception ex) {
        return ex.getMessage();
    }
}
