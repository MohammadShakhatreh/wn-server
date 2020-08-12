package com.worldnavigator.web.controllers;

import com.worldnavigator.web.entities.User;
import com.worldnavigator.web.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AuthenticationControllerAdvice {

    @ModelAttribute
    public User principal(Authentication authentication) {
        return authentication == null ? null : (User) authentication.getPrincipal();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(UserNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Getter
    @AllArgsConstructor
    static class ErrorMessage {
        private final String message;
    }
}
