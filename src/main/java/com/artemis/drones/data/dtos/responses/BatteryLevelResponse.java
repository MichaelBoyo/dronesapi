package com.artemis.drones.data.dtos.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class BatteryLevelResponse {
    private Double batteryLevel;
}
