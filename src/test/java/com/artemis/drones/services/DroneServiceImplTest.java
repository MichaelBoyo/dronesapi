package com.artemis.drones.services;

import com.artemis.drones.data.dtos.requests.DroneRegistrationRequest;
import com.artemis.drones.data.dtos.requests.LoadDroneRequest;
import com.artemis.drones.data.dtos.responses.BatteryLevelResponse;
import com.artemis.drones.data.dtos.responses.DroneRegistrationResponse;
import com.artemis.drones.data.dtos.responses.LoadDroneResponse;
import com.artemis.drones.data.models.Drone;
import com.artemis.drones.data.models.Medication;
import com.artemis.drones.data.models.Model;
import com.artemis.drones.exceptions.DroneBatteryLevelLowException;
import com.artemis.drones.exceptions.DroneNotfoundException;
import com.artemis.drones.exceptions.MaximumDroneWeightException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DroneServiceImplTest {
    @Autowired
    private DroneService droneService;

    private DroneRegistrationResponse response;
    LoadDroneRequest loadDroneRequest;

    @BeforeEach
    void setUp() {
        DroneRegistrationRequest droneRegistrationRequest = DroneRegistrationRequest.builder()
                .model(Model.CRUISERWEIGHT)
                .serialNumber("1343")
                .build();
        response =
                droneService.registerDrone(droneRegistrationRequest);

        loadDroneRequest = LoadDroneRequest.builder()
                .droneId(response.getDrone().getId())
                .name("test madication")
                .code("test code")
                .weight(35.0)
                .imageUrl("test url")
                .build();
    }

    @AfterEach
    void tearDown() {
        droneService.clearTestDatabase();
    }


    @Test
    @DisplayName("test that drone can be registered")
    void registerDrone() {
        assertThat(response).isNotNull();
        assertThat(response.getDrone()).isNotNull();
    }

    @Test
    @DisplayName("test that drone can be loaded")
    void loadDrone() {

        LoadDroneResponse res;
        try {
            res = droneService.loadDrone(loadDroneRequest);
            assertThat(res).isNotNull();
            assertThat(res.getDrone().getWeightLimit()).isEqualTo(465.0);

            assertThat(res.getDrone().getMedications()).isNotNull();
        } catch (DroneNotfoundException | MaximumDroneWeightException | DroneBatteryLevelLowException exception) {
            exception.printStackTrace();
        }


    }

    @Test
    @DisplayName("test that drone not found exception is thrown")
    void testThatDroneNotFoundExceptionIsThrown() {
        assertThrows(DroneNotfoundException.class,
                () -> droneService.loadDrone(LoadDroneRequest.builder()
                        .droneId(167L)
                        .build()));
    }

    @Test
    @DisplayName("test that loaded medications can be gotten")
    void getLoadedMedications() {
        try {
            droneService.loadDrone(loadDroneRequest);
            droneService.loadDrone(loadDroneRequest);
            List<Medication> loadedMedications = droneService.getLoadedMedications(loadDroneRequest.getDroneId());
            assertThat(loadedMedications).isNotNull();
            assertThat(loadedMedications.size()).isEqualTo(2);
        } catch (DroneNotfoundException | MaximumDroneWeightException | DroneBatteryLevelLowException exception) {
            exception.printStackTrace();
        }

    }

    @Test
    @DisplayName("test that maximum drone weight exception is thrown")
    void testThatMaximumDroneWeightExceptionISThrown() {
        int i = 0;
        while (i < 14) {
            try {
                droneService.loadDrone(loadDroneRequest);
            } catch (DroneNotfoundException | MaximumDroneWeightException | DroneBatteryLevelLowException exception) {
                exception.printStackTrace();
            }
            i++;
        }
        assertThrows(MaximumDroneWeightException.class,
                () -> droneService.loadDrone(loadDroneRequest));
    }

    @Test
    @DisplayName("test get available drones for loading")
    void getAvailableDronesForLoading() {
        try {
            droneService.loadDrone(loadDroneRequest);
        } catch (DroneNotfoundException | MaximumDroneWeightException | DroneBatteryLevelLowException exception) {
            exception.printStackTrace();
        }

        droneService.registerDrone(DroneRegistrationRequest.builder()
                .model(Model.HEAVYWEIGHT)
                .serialNumber("493093")
                .build());

        assertThat(droneService.getAvailableDronesForLoading()).isNotNull();
    }

    @Test
    @DisplayName("test that the drone is not in loading state when battery is below 25%" +
            "")
    void testThatDroneIsNotLoadedWithMoreWeightThanItCanCarry() {
        try {
            Drone drone = droneService.getDrone(response.getDrone().getId());
            drone.setBatteryCapacity(24.0);
            droneService.saveDrone(drone);
            assertThrows(DroneBatteryLevelLowException.class,
                    () -> droneService.loadDrone(loadDroneRequest));
        } catch (DroneNotfoundException e) {
            e.printStackTrace();
        }

    }


    @Test
    void getBatteryLevelOfDrone() throws DroneNotfoundException {
        BatteryLevelResponse level = droneService.getBatteryLevelOfDrone(response.getDrone().getId());
        assertThat(level.getBatteryLevel()).isEqualTo(100.0);
    }
}