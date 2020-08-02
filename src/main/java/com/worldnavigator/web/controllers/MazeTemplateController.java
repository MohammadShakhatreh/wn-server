package com.worldnavigator.web.controllers;

import com.worldnavigator.game.maze.Maze;
import com.worldnavigator.web.services.MazeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("mazes")
public class MazeTemplateController {

    private final MazeTemplateService mazeTemplateService;

    @Autowired
    public MazeTemplateController(MazeTemplateService mazeTemplateService) {
        this.mazeTemplateService = mazeTemplateService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody Maze maze) {

    }
}
