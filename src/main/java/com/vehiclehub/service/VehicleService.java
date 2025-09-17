package com.vehiclehub.service;

import com.vehiclehub.DTO.VehicleRequestDto;
import com.vehiclehub.DTO.VehicleResponseDto;
import com.vehiclehub.entity.Dealer;
import com.vehiclehub.entity.Vehicle;
import com.vehiclehub.entity.VehicleStatus;
import com.vehiclehub.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DealerService dealerService;

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicle(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle updateVehicle(Long id, VehicleRequestDto dto) {
        Vehicle vehicle = getVehicle(id);

        Dealer dealer = dealerService.getDealerById(dto.getDealerId());
        if (dealer == null) {
            throw new RuntimeException("Dealer not found with id: " + dto.getDealerId());
        }

        vehicle.setModel(dto.getModel());
        vehicle.setPrice(dto.getPrice());

        try {
            VehicleStatus status = VehicleStatus.valueOf(dto.getStatus().toUpperCase());
            vehicle.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid vehicle status: " + dto.getStatus());
        }

        vehicle.setDealer(dealer);

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = getVehicle(id);
        vehicleRepository.delete(vehicle);
    }

    public Vehicle updateVehicleStatus(Long vehicleId, VehicleStatus status) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicle.setStatus(status);
        return vehicleRepository.save(vehicle);
    }

    // Mapper method: Vehicle -> VehicleResponseDto
    public VehicleResponseDto mapToDto(Vehicle vehicle) {
        return new VehicleResponseDto(
                vehicle.getId(),
                vehicle.getModel(),
                vehicle.getPrice(),
                vehicle.getYear(),
                vehicle.getStatus(),
                vehicle.getDealer() != null ? vehicle.getDealer().getId() : null
        );
    }

    // Mapper method: list of Vehicles -> list of VehicleResponseDto
    public List<VehicleResponseDto> mapToDtoList(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
