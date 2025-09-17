package com.vehiclehub.service;

import com.vehiclehub.DTO.PaymentRequestDto;
import com.vehiclehub.entity.Payment;
import com.vehiclehub.entity.PaymentStatus;
import com.vehiclehub.entity.User;
import com.vehiclehub.entity.Vehicle;
import com.vehiclehub.entity.VehicleStatus;
import com.vehiclehub.exception.ResourceNotFoundException;
import com.vehiclehub.repository.PaymentRepository;
import com.vehiclehub.repository.UserRepository;
import com.vehiclehub.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment initiatePayment(PaymentRequestDto dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + dto.getVehicleId()));

        if (vehicle.getStatus() == VehicleStatus.SOLD) {
            throw new IllegalStateException("Vehicle is already sold");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));

        Payment payment = new Payment();
        payment.setVehicleId(vehicle.getId());
        payment.setAmount(dto.getAmount());
        payment.setMethod(dto.getMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTimestamp(LocalDateTime.now());
        payment.setUser(user);

        Payment savedPayment = paymentRepository.save(payment);

        vehicle.setStatus(VehicleStatus.SOLD);
        vehicleRepository.save(vehicle);

        simulateCallback(savedPayment);

        return savedPayment;
    }

    @Async
    public void simulateCallback(Payment payment) {
        try {
            Thread.sleep(5000);
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
