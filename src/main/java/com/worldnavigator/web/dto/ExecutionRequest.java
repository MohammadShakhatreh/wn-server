package com.worldnavigator.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ExecutionRequest {

    @NotBlank
    private final String line;

    @JsonCreator
    public ExecutionRequest(
            @JsonProperty String line
    ) {
        this.line = line;
    }
}
