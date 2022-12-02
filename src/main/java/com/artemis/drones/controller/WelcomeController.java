package com.artemis.drones.controller;

import com.artemis.drones.services.DroneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WelcomeController {
    private final DroneService droneService;
    @GetMapping
    public String welcome() {
        return "Welcome to Drones Api";
    }
    @GetMapping("all_drones")
    public ResponseEntity<?> getAllDrones(){
        return ResponseEntity.ok(droneService.getALlDrones());
    }
}
