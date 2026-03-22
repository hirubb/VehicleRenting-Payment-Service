package com.paymentService.service;

import com.paymentService.dto.PaymentRequest;
import com.paymentService.dto.PaymentResponse;
import com.paymentService.exception.PaymentNotFoundException;
import com.paymentService.model.Payment;
import com.paymentService.model.PaymentStatus;
import com.paymentService.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository repository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;

    public PaymentService(PaymentRepository repository, KafkaProducerService kafkaProducerService, ObjectMapper objectMapper) {
        this.repository = repository;
        this.kafkaProducerService = kafkaProducerService;
        this.objectMapper = objectMapper;
    }

    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.SUCCESS); // In real apps, call a payment gateway here

        Payment saved = repository.save(payment);
        PaymentResponse response = mapToResponse(saved);

        // Send Kafka event to Customer Service
        try {
            String message = objectMapper.writeValueAsString(response);
            kafkaProducerService.sendPaymentStatus("payment-success-topic", message);
        } catch (JsonProcessingException e) {
            logger.error("Error serializing payment response for Kafka", e);
        }

        return response;
    }

    public PaymentResponse getPaymentById(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        return mapToResponse(payment);
    }

    public PaymentResponse getByBookingId(String bookingId) {
        Payment payment = repository.findByBookingId(bookingId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found for booking: " + bookingId));
        return mapToResponse(payment);
    }

    public PaymentResponse refundPayment(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        payment.setStatus(PaymentStatus.REFUNDED);
        return mapToResponse(repository.save(payment));
    }

    public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        return repository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setBookingId(payment.getBookingId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getStatus());
        response.setCreatedAt(payment.getCreatedAt());
        return response;
    }
}