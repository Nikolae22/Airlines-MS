package com.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentLinkResponse {

    private Long id;
    private String payment_link_url;
    private String payment_link_id;
}
