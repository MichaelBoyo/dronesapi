package com.artemis.drones.controller;

import com.artemis.drones.exceptions.DroneException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleExceptions(DroneException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
