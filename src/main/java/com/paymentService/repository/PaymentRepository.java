package com.paymentService.repository;

import com.paymentService.model.Payment;
import com.paymentService.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBookingId(String bookingId);
    List<Payment> findByStatus(PaymentStatus status);
}