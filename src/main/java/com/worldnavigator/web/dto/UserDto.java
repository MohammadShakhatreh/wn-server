package com.worldnavigator.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Must be not blank.")
    private final String name;

    @Pattern(
            regexp = "^[\\w.]+$",
            message = "Usernames can only use letters, numbers, underscores and periods."
    )
    private final String username;

    @NotBlank(message = "Must be not blank.")
    private final String password;
}
