package com.artemis.drones.data.dtos.requests;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoadDroneRequest {
    private Long droneId;
    private String name;
    private Double weight;
    private String imageUrl;
    private String code;
}
