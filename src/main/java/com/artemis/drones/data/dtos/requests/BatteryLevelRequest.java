package com.artemis.drones.data.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BatteryLevelRequest {
    private Long droneId;
}
