package com.worldnavigator.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class AccountDto {

    @NotBlank
    private final String name;

    @Pattern(
            regexp = "^[\\w.]+$",
            message = "Usernames can only use letters, numbers, underscores and periods."
    )
    private final String username;

    @NotBlank
    private final String password;
}
