package com.project.dvManagement.controller;

import com.project.dvManagement.DTO.VehicleRequestDto;
import com.project.dvManagement.entity.Dealer;
import com.project.dvManagement.entity.Vehicle;
import com.project.dvManagement.entity.VehicleStatus;
import com.project.dvManagement.service.DealerService;
import com.project.dvManagement.service.VehicleService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DealerService dealerService;

    @PostMapping
    public Vehicle createVehicle(@Valid @RequestBody VehicleRequestDto dto) {
        Dealer dealer = dealerService.getDealerById(dto.getDealerId());
        if (dealer == null) {
            throw new RuntimeException("Dealer not found with id: " + dto.getDealerId());
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setModel(dto.getModel());
        vehicle.setPrice((double) dto.getPrice());

        // Convert String status to VehicleStatus enum
        try {
            VehicleStatus status = VehicleStatus.valueOf(dto.getStatus().toUpperCase());
            vehicle.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid vehicle status: " + dto.getStatus());
        }

        vehicle.setDealer(dealer);

        return vehicleService.saveVehicle(vehicle);
    }


}
