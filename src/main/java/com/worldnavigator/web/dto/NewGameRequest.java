package com.worldnavigator.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class NewGameRequest {

    @NotBlank(message = "Must be not blank.")
    private final String name;

    @Min(value = 0, message = "Must be not greater than zero.")
    private final long mazeId;

    @Min(value = 0, message = "Must be non-negative integer.")
    private final int gold;

    @Min(value = 1, message = "Must be an integer greater than zero.")
    private final int timeout;

    @Min(value = 0, message = "Must be an integer greater than zero.")
    private final long startsAfter;
}
