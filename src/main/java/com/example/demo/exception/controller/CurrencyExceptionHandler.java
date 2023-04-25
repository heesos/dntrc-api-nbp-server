package com.example.demo.exception.controller;

import com.example.demo.exception.CurrencyNotFoundException;
import com.example.demo.exception.InvalidLocalDateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CurrencyExceptionHandler {

    @ExceptionHandler(value = InvalidLocalDateException.class)
    public ResponseEntity<Object> handleInvalidLocalDate(InvalidLocalDateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CurrencyNotFoundException.class)
    public ResponseEntity<Object> handleCurrencyNotFound(CurrencyNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
