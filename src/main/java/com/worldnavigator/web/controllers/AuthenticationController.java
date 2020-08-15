package com.worldnavigator.web.controllers;

import com.worldnavigator.web.jwt.JwtConfiguration;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.util.Date.from;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final JwtConfiguration jwtConfiguration;
    private final SecretKey secretKey;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(
            JwtConfiguration jwtConfiguration,
            SecretKey secretKey,
            AuthenticationManager authenticationManager
    ) {

        this.jwtConfiguration = jwtConfiguration;
        this.secretKey = secretKey;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtToken login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword());

        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Instant now = Instant.now();

        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(from(now))
                .setExpiration(from(now.plus(jwtConfiguration.getExpireInDays(), ChronoUnit.DAYS)))
                .signWith(secretKey)
                .compact();

        return new JwtToken(token);
    }

    @Getter
    @AllArgsConstructor
    private static class LoginRequest {

        @NotBlank
        private final String username;

        @NotBlank
        private final String password;
    }

    @Getter
    @AllArgsConstructor
    private static class JwtToken {

        private final String token;
    }
}
