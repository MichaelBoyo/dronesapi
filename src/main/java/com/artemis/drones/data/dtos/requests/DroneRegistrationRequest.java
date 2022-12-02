package com.artemis.drones.data.dtos.requests;

import com.artemis.drones.data.models.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class DroneRegistrationRequest {
    private Model model;
    private String serialNumber;

}
