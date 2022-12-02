package com.artemis.drones.dataLoader;

import com.artemis.drones.data.dtos.requests.DroneRegistrationRequest;
import com.artemis.drones.services.DroneService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.artemis.drones.data.models.Model.*;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final DroneService droneService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<DroneRegistrationRequest> drones = List.of(DroneRegistrationRequest.builder()
                        .model(HEAVYWEIGHT)
                        .serialNumber("101011").build(),
                DroneRegistrationRequest.builder()
                        .model(CRUISERWEIGHT)
                        .serialNumber("101012").build(),
                DroneRegistrationRequest.builder()
                        .model(LIGHTWEIGHT)
                        .serialNumber("101013").build(),
                DroneRegistrationRequest.builder()
                        .model(MIDDLEWEIGHT)
                        .serialNumber("101014").build(),
                DroneRegistrationRequest.builder()
                        .model(HEAVYWEIGHT)
                        .serialNumber("101015").build(),
                DroneRegistrationRequest.builder()
                        .model(CRUISERWEIGHT)
                        .serialNumber("101016").build(),
                DroneRegistrationRequest.builder()
                        .model(LIGHTWEIGHT)
                        .serialNumber("101017").build(),
                DroneRegistrationRequest.builder()
                        .model(HEAVYWEIGHT)
                        .serialNumber("101018").build(),
                DroneRegistrationRequest.builder()
                        .model(MIDDLEWEIGHT)
                        .serialNumber("101019").build(),
                DroneRegistrationRequest.builder()
                        .model(HEAVYWEIGHT)
                        .serialNumber("101100").build()

        );
        drones.forEach(droneService::registerDrone);
    }
}
