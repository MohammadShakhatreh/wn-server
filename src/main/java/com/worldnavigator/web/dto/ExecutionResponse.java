package com.worldnavigator.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ExecutionResponse {

    @NotBlank
    private final String result;

    @JsonCreator
    public ExecutionResponse(
            @JsonProperty String result
    ) {
        this.result = result;
    }
}
