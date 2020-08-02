package com.worldnavigator.web.dto;

import com.worldnavigator.game.maze.Maze;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class NewGameRequest {

    @NotBlank(message = "Must be not blank.")
    private final String name;

    @NotNull(message = "Must be not null.")
    private final Maze maze;

    @Min(value = 0, message = "Must be non-negative integer.")
    private final int gold;

    @Min(value = 1, message = "Must be an integer greater than zero.")
    private final int timeout;

    @Min(value = 0, message = "Must be an integer greater than zero.")
    private final long startsAfter;
}
