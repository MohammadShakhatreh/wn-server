package com.worldnavigator.web.controllers;

import com.worldnavigator.web.dto.UserDto;
import com.worldnavigator.web.entities.User;
import com.worldnavigator.web.exceptions.UserNotFoundException;
import com.worldnavigator.web.repositories.UserRepository;
import com.worldnavigator.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserDto account) {
        return userService.create(account);
    }

    @GetMapping("@{username}")
    public User retrieve(@PathVariable String username) {
        return userRepository
                .findById(username)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("There is no account for %s!", username)));
    }

    @DeleteMapping("@{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String username) {
        userRepository.deleteById(username);
    }

    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("me")
    public User me(User user) {
        return user;
    }
}
