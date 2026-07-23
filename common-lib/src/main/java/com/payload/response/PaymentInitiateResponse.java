package com.payload.response;

import com.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInitiateResponse {

    private Long paymentId;
    private PaymentGateway gateway;
    private String transactionId;

    //razor pay
    private String razorpayOrderId;

    private Double amount;
    private String currency;
    private String description;

    //fornted should redired user to this url for patment
    private String checkoutUrl;

    private String message;
    private Boolean success;
}
