package com.project.dvManagement.controller;

import com.project.dvManagement.DTO.PaymentRequestDto;
import com.project.dvManagement.entity.Payment;
import com.project.dvManagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<Payment> initiate(@RequestBody PaymentRequestDto dto) {
        Payment payment = paymentService.initiatePayment(dto);
        return ResponseEntity.ok(payment);
    }
}
