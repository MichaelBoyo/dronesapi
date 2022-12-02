package com.artemis.drones.exceptions;

public class DroneBatteryLevelLowException extends DroneException {
    public DroneBatteryLevelLowException(String message) {
        super(message);
    }
}
