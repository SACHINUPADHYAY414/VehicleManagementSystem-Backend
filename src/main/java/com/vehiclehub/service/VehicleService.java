package com.vehiclehub.service;

import com.vehiclehub.DTO.VehicleRequestDto;
import com.vehiclehub.DTO.VehicleResponseDto;
import com.vehiclehub.entity.Dealer;
import com.vehiclehub.entity.Vehicle;
import com.vehiclehub.entity.VehicleStatus;
import com.vehiclehub.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DealerService dealerService;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Create Vehicle with Image
    @Transactional
    public Vehicle saveVehicleWithImage(VehicleRequestDto dto) {
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

        // Upload image if provided
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(dto.getImage());
            vehicle.setImageUrl(imageUrl);
        }

        return vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicle(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Update Vehicle (with optional image update)
    @Transactional
    public Vehicle updateVehicle(Long id, VehicleRequestDto dto) {
        Vehicle vehicle = getVehicle(id);

        Dealer dealer = dealerService.getDealerById(dto.getDealerId());
        if (dealer == null) {
            throw new RuntimeException("Dealer not found with id: " + dto.getDealerId());
        }

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

        // Replace image if new one uploaded
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(dto.getImage());
            vehicle.setImageUrl(imageUrl);
        }

        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        Vehicle vehicle = getVehicle(id);
        vehicleRepository.delete(vehicle);
    }

    @Transactional
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
                vehicle.getDealer() != null ? vehicle.getDealer().getId() : null,
                vehicle.getImageUrl()
        );
    }

    // Mapper method: list of Vehicles -> list of VehicleResponseDto
    public List<VehicleResponseDto> mapToDtoList(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
