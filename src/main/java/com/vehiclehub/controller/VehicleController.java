package com.vehiclehub.controller;

import com.vehiclehub.DTO.VehicleRequestDto;
import com.vehiclehub.DTO.VehicleResponseDto;
import com.vehiclehub.entity.Dealer;
import com.vehiclehub.entity.Vehicle;
import com.vehiclehub.entity.VehicleStatus;
import com.vehiclehub.service.DealerService;
import com.vehiclehub.service.VehicleService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DealerService dealerService;

    // Create Vehicle
    @PostMapping
    public VehicleResponseDto createVehicle(@Valid @RequestBody VehicleRequestDto dto) {
        Dealer dealer = dealerService.getDealerById(dto.getDealerId());
        if (dealer == null) {
            throw new RuntimeException("Dealer not found with id: " + dto.getDealerId());
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setModel(dto.getModel());
        vehicle.setPrice(dto.getPrice());
        vehicle.setYear(dto.getYear());

        try {
            VehicleStatus status = VehicleStatus.valueOf(dto.getStatus().toUpperCase());
            vehicle.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid vehicle status: " + dto.getStatus());
        }

        vehicle.setDealer(dealer);

        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
        return vehicleService.mapToDto(savedVehicle);
    }

    // Get all Vehicles
    @GetMapping("/all")
    public List<VehicleResponseDto> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return vehicleService.mapToDtoList(vehicles);
    }

    // Get Vehicle by ID
    @GetMapping("/{id}")
    public VehicleResponseDto getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getVehicle(id);
        return vehicleService.mapToDto(vehicle);
    }

    // Update Vehicle
    @PutMapping("/{id}")
    public VehicleResponseDto updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequestDto dto) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, dto);
        return vehicleService.mapToDto(updatedVehicle);
    }

    // Delete Vehicle
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}
