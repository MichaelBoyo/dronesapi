package com.artemis.drones.services;

import com.artemis.drones.data.dtos.requests.DroneRegistrationRequest;
import com.artemis.drones.data.dtos.requests.LoadDroneRequest;
import com.artemis.drones.data.dtos.responses.BatteryLevelResponse;
import com.artemis.drones.data.dtos.responses.DroneRegistrationResponse;
import com.artemis.drones.data.dtos.responses.LoadDroneResponse;
import com.artemis.drones.data.models.Drone;
import com.artemis.drones.data.models.Medication;
import com.artemis.drones.exceptions.DroneBatteryLevelLowException;
import com.artemis.drones.exceptions.DroneNotfoundException;
import com.artemis.drones.exceptions.MaximumDroneWeightException;

import java.util.List;

public interface DroneService {
    DroneRegistrationResponse registerDrone(DroneRegistrationRequest droneRegistrationRequest);
    LoadDroneResponse loadDrone(LoadDroneRequest loadDroneRequest) throws DroneNotfoundException, MaximumDroneWeightException, DroneBatteryLevelLowException;
    List<Medication> getLoadedMedications(Long droneID) throws DroneNotfoundException;
    List<Drone> getAvailableDronesForLoading();
    BatteryLevelResponse getBatteryLevelOfDrone(Long serialNumber) throws DroneNotfoundException;

    void clearTestDatabase();

    Drone getDrone(Long id) throws DroneNotfoundException;

    void saveDrone(Drone drone);

    List<Drone> getALlDrones();
}
