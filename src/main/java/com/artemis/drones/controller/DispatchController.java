package com.artemis.drones.controller;

import com.artemis.drones.data.dtos.requests.BatteryLevelRequest;
import com.artemis.drones.data.dtos.requests.DroneRegistrationRequest;
import com.artemis.drones.data.dtos.requests.LoadDroneRequest;
import com.artemis.drones.data.dtos.requests.LoadedMedicationRequest;
import com.artemis.drones.exceptions.DroneBatteryLevelLowException;
import com.artemis.drones.exceptions.DroneNotfoundException;
import com.artemis.drones.exceptions.MaximumDroneWeightException;
import com.artemis.drones.services.DroneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drone")
@AllArgsConstructor
public class DispatchController {
    private final DroneService droneService;
    @PostMapping("/register")
    public ResponseEntity<?> registerDrone(@RequestBody  DroneRegistrationRequest request){
        return ResponseEntity.ok(droneService.registerDrone(request));
    }
    @PutMapping("/load")
    public ResponseEntity<?> loadDrone(@RequestBody LoadDroneRequest request) throws DroneNotfoundException, MaximumDroneWeightException, DroneBatteryLevelLowException {
        return ResponseEntity.ok(droneService.loadDrone(request));
    }
    @GetMapping("/loaded_medications")
    public ResponseEntity<?> getLoadedMedications(@RequestBody LoadedMedicationRequest request) throws DroneNotfoundException {
        return ResponseEntity.ok(droneService.getLoadedMedications(request.getDroneId()));
    }
    @GetMapping("/available_drones")
    public ResponseEntity<?> getAvailableDronesForLoading(){
        return ResponseEntity.ok(droneService.getAvailableDronesForLoading());
    }
    @GetMapping("/battery_level")
    public ResponseEntity<?> getBatteryLevelOfDrone(@RequestBody BatteryLevelRequest batteryLevelRequest) throws DroneNotfoundException {
       return ResponseEntity.ok(droneService.getBatteryLevelOfDrone(batteryLevelRequest.getDroneId()));
    }

}
