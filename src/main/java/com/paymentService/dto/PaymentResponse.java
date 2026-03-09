package com.paymentService.dto;


import com.paymentService.model.PaymentMethod;
import com.paymentService.model.PaymentStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Long id;
    private String bookingId;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}