package com.vehiclehub.controller;

import com.vehiclehub.DTO.VehicleRequestDto;
import com.vehiclehub.DTO.VehicleResponseDto;
import com.vehiclehub.entity.Vehicle;
import com.vehiclehub.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Create Vehicle (with image)
    @PostMapping(consumes = {"multipart/form-data"})
    public VehicleResponseDto createVehicle(@ModelAttribute @Valid VehicleRequestDto dto) {
        Vehicle savedVehicle = vehicleService.saveVehicleWithImage(dto);
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

    // Update Vehicle (with optional image update)
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public VehicleResponseDto updateVehicle(@PathVariable Long id,
                                            @ModelAttribute @Valid VehicleRequestDto dto) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, dto);
        return vehicleService.mapToDto(updatedVehicle);
    }

    // Delete Vehicle
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}
