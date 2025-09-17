package com.vehiclehub.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vehiclehub.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
