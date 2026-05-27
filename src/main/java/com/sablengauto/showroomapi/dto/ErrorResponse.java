package com.sablengauto.showroomapi.dto;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;

    private Map<String, String> errors;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message,
            Map<String, String> errors) {

        this.message = message;
        this.errors = errors;

    }
}
