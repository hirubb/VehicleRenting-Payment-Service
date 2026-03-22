package com.paymentService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paymentService.model.PaymentMethod;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank(message = "Booking ID is required")
    private String bookingId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    // Mock Card Details (Only required for CREDIT_CARD and DEBIT_CARD)
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    @JsonIgnore
    @AssertTrue(message = "Card details (cardNumber, expiryDate, cvv) are required when payment method is CREDIT_CARD or DEBIT_CARD")
    public boolean isCardDetailsValid() {
        if (paymentMethod == PaymentMethod.CREDIT_CARD || paymentMethod == PaymentMethod.DEBIT_CARD) {
            return cardNumber != null && !cardNumber.trim().isEmpty() &&
                   expiryDate != null && !expiryDate.trim().isEmpty() &&
                   cvv != null && !cvv.trim().isEmpty();
        }
        return true;
    }
}