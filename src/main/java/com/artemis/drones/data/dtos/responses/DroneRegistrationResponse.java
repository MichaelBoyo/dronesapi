package com.artemis.drones.data.dtos.responses;

import com.artemis.drones.data.models.Drone;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DroneRegistrationResponse {
    private String message;
    private int code;
    private Drone drone;
}
