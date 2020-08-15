package com.worldnavigator.web.controllers;

import com.worldnavigator.web.dto.MazeTemplateInfo;
import com.worldnavigator.web.mappers.GameMapper;
import com.worldnavigator.web.repositories.MazeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("mazes")
public class MazeController {

    private final GameMapper gameMapper;

    private final MazeTemplateRepository mazeTemplateRepository;

    @Autowired
    public MazeController(GameMapper gameMapper, MazeTemplateRepository mazeTemplateRepository) {
        this.gameMapper = gameMapper;
        this.mazeTemplateRepository = mazeTemplateRepository;
    }

    @GetMapping
    public List<MazeTemplateInfo> list() {
        return mazeTemplateRepository.findAll()
                .stream()
                .map(gameMapper::toMazeTemplateInfo)
                .collect(toList());
    }
}
