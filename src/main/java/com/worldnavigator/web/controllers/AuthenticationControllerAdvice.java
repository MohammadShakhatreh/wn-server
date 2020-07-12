package com.worldnavigator.web.controllers;

import com.worldnavigator.web.entities.Account;
import com.worldnavigator.web.exceptions.AccountNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AuthenticationControllerAdvice extends ResponseEntityExceptionHandler {

    @ModelAttribute
    public Account principal(Authentication authentication) {
        return authentication == null ? null : (Account) authentication.getPrincipal();
    }


    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(AccountNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Getter
    @AllArgsConstructor
    static class ErrorMessage {
        private final String message;
    }
}
