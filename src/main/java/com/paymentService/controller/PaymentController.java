package com.paymentService.controller;

import com.paymentService.model.Payment;
import com.paymentService.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return service.createPayment(payment);
    }

    @GetMapping("/{id}")
    public Optional<Payment> getPayment(@PathVariable Long id) {
        return service.getPayment(id);
    }

    @GetMapping("/booking/{bookingId}")
    public Optional<Payment> getByBookingId(@PathVariable String bookingId) {
        return service.getByBookingId(bookingId);
    }
}
