package com.filiaiev.chargeservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ChargeException extends RuntimeException {

    private final HttpStatus statusCode;

    public ChargeException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ChargeException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
