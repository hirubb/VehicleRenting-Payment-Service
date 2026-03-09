package com.paymentService.controller;

import com.paymentService.dto.PaymentRequest;
import com.paymentService.dto.PaymentResponse;
import com.paymentService.model.PaymentStatus;
import com.paymentService.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPayment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPaymentById(id));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponse> getByBookingId(@PathVariable String bookingId) {
        return ResponseEntity.ok(service.getByBookingId(bookingId));
    }

    @PutMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(@PathVariable Long id) {
        return ResponseEntity.ok(service.refundPayment(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponse>> getByStatus(@PathVariable PaymentStatus status) {
        return ResponseEntity.ok(service.getPaymentsByStatus(status));
    }
}