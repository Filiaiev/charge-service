package com.filiaiev.chargeservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private Instant time;
    private String message;
    private String details;

    public ErrorDetails(String message, String details) {
        this(Instant.now(), message, details);
    }
}
