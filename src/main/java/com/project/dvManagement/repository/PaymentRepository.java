package com.project.dvManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.dvManagement.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}