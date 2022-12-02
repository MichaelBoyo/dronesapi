package com.artemis.drones.exceptions;

public class DroneException extends Exception {
    public DroneException(String message) {
        super(message);
    }

    public DroneException() {
    }

    public DroneException(String message, Throwable cause) {
        super(message, cause);
    }

    public DroneException(Throwable cause) {
        super(cause);
    }
}
