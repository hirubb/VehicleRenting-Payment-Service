package com.paymentService.service;

import com.paymentService.model.Payment;
import com.paymentService.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment createPayment(Payment payment) {
        payment.setStatus("SUCCESS");
        return repository.save(payment);
    }

    public Optional<Payment> getPayment(Long id) {
        return repository.findById(id);
    }

    public Optional<Payment> getByBookingId(String bookingId) {
        return repository.findByBookingId(bookingId);
    }
}