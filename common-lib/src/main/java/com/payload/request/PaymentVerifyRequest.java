package com.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVerifyRequest {

    //razor pay
    private String razorpayPaymentId;
//    private String razorpayOrderId;
//    private String razorpaySignature;

    //stripe specific fields
    private String stripePaymentIntentId;
    //private String stripePaymentIntentStatus;
}
