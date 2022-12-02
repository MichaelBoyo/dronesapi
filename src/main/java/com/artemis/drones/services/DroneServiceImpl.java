package com.artemis.drones.services;

import com.artemis.drones.data.dtos.requests.DroneRegistrationRequest;
import com.artemis.drones.data.dtos.requests.LoadDroneRequest;
import com.artemis.drones.data.dtos.responses.BatteryLevelResponse;
import com.artemis.drones.data.dtos.responses.DroneRegistrationResponse;
import com.artemis.drones.data.dtos.responses.LoadDroneResponse;
import com.artemis.drones.data.models.Drone;
import com.artemis.drones.data.models.Log;
import com.artemis.drones.data.models.Medication;
import com.artemis.drones.data.models.State;
import com.artemis.drones.data.repository.DroneRepository;
import com.artemis.drones.exceptions.DroneBatteryLevelLowException;
import com.artemis.drones.exceptions.DroneNotfoundException;
import com.artemis.drones.exceptions.MaximumDroneWeightException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;

    @Override
    public DroneRegistrationResponse registerDrone(DroneRegistrationRequest droneRegistrationRequest) {
        log.info("registering drone --> {}", droneRegistrationRequest);
        Drone drone = buildDrone(droneRegistrationRequest);
        drone = droneRepository.save(drone);
        return buildRegisterResponse(drone);
    }

    private static DroneRegistrationResponse buildRegisterResponse(Drone drone) {
        return DroneRegistrationResponse.builder()
                .drone(drone)
                .message("Drone registered successfully")
                .code(200)
                .build();
    }

    private static Drone buildDrone(DroneRegistrationRequest droneRegistrationRequest) {
        return Drone.builder()
                .model(droneRegistrationRequest.getModel())
                .batteryCapacity(100.0)
                .weightLimit(500.0)
                .serialNumber(droneRegistrationRequest.getSerialNumber())
                .state(State.IDLE)
                .logs(new HashSet<>())
                .medications(new HashSet<>())
                .build();
    }

    @Override
    public LoadDroneResponse loadDrone(LoadDroneRequest loadDroneRequest) throws DroneNotfoundException,
            MaximumDroneWeightException, DroneBatteryLevelLowException {
        log.info("loading drone --> {}", loadDroneRequest);
        Drone drone = getDroneFromDatabase(loadDroneRequest.getDroneId());

        checkDroneStatus(loadDroneRequest, drone);
        Medication medication = buildMedication(loadDroneRequest);
        drone = setUpDrone(loadDroneRequest, drone, medication);
        return buildLoadDroneResponse(drone);
    }

    private static LoadDroneResponse buildLoadDroneResponse(Drone drone) {
        return LoadDroneResponse.builder()
                .drone(drone)
                .message("drone loaded successfully")
                .code(200)
                .build();
    }

    private Drone setUpDrone(LoadDroneRequest loadDroneRequest, Drone drone, Medication medication) {
        drone.setState(State.LOADING);
        drone.getMedications().add(medication);
        drone.setWeightLimit(drone.getWeightLimit() - loadDroneRequest.getWeight());
        if(drone.getWeightLimit() == 0.0) drone.setState(State.LOADED);
        drone.getLogs().add(Log.builder()
                .batteryLevel(drone.getBatteryCapacity())
                .dateLogged(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, hh:mm:ss")))
                .build());
        drone = droneRepository.save(drone);

        return drone;
    }

    private void checkDroneStatus(LoadDroneRequest loadDroneRequest, Drone drone) throws MaximumDroneWeightException, DroneBatteryLevelLowException {
        if(drone.getState().equals(State.LOADED)){
            throw new MaximumDroneWeightException("drone fully loaded");
        }
        if (drone.getWeightLimit() < loadDroneRequest.getWeight()) {
            throw new MaximumDroneWeightException("drone weight limit is exceeded, can only take " + drone.getWeightLimit() + "kg more");
        }
        if (drone.getBatteryCapacity() < 25.0) {
            throw new DroneBatteryLevelLowException("battery level of drone is below 25%");
        }
    }

    private static Medication buildMedication(LoadDroneRequest loadDroneRequest) {
        return Medication.builder()
                .name(loadDroneRequest.getName())
                .weight(loadDroneRequest.getWeight())
                .code(loadDroneRequest.getCode())
                .imageUrl(loadDroneRequest.getImageUrl())
                .build();

    }

    @Override
    public List<Medication> getLoadedMedications(Long droneId) throws DroneNotfoundException {
        log.info("getting loaded medications of drone with id {}", droneId);
        Drone drone = getDroneFromDatabase(droneId);
        return drone.getMedications().stream().toList();
    }

    @Override
    public List<Drone> getAvailableDronesForLoading() {
        log.info("getting all available drones for loading");
        return droneRepository.findAll().stream()
                .filter(drone -> drone.getState().equals(State.IDLE) || drone.getState().equals(State.LOADING)).toList();
    }

    @Override
    public BatteryLevelResponse getBatteryLevelOfDrone(Long id) throws DroneNotfoundException {
        Drone drone = getDroneFromDatabase(id);
       return BatteryLevelResponse.builder()
               .batteryLevel(drone.getBatteryCapacity())
               .build();
    }

    private Drone getDroneFromDatabase(Long droneId) throws DroneNotfoundException {
        return droneRepository.findById(droneId).orElseThrow(
                () -> new DroneNotfoundException("drone with id " + droneId + " not found"));
    }

    @Override
    public void clearTestDatabase() {
        droneRepository.deleteAll();
    }

    @Override
    public Drone getDrone(Long id) throws DroneNotfoundException {
        return getDroneFromDatabase(id);
    }

    @Override
    public void saveDrone(Drone drone) {
        droneRepository.save(drone);
    }

    @Override
    public List<Drone> getALlDrones() {
        return droneRepository.findAll();
    }
}
