package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used in case of wrongly provided date
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLocalDateException extends RuntimeException {
    public InvalidLocalDateException(String message) {
        super(message);
    }
}
