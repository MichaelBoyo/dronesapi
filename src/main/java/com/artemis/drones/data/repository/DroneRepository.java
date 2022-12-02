package com.artemis.drones.data.repository;

import com.artemis.drones.data.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
