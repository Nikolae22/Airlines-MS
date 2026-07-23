package com.payload.request;

import com.enums.PaymentGateway;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInitiateRequest {

    @NotNull(message = "user id is required")
    private Long userId;

    @NotNull(message = "bookign id required")
    private Long bookingId;

    @NotNull(message = "Payment gateway is required")
    private PaymentGateway gateway;

    @NotNull(message = "Amount is required")
    @Positive(message = "Should be positive")
    private Double amount;

    private String description;


}
