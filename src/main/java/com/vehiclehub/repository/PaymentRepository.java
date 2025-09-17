package com.vehiclehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vehiclehub.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
