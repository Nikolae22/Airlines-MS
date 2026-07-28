package com.bookingservice.services.integration;

import com.bookingservice.client.PricingClient;
import com.payload.response.FareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FareIntegrationService {

    private final PricingClient pricingClient;

    public Double calculateFareTotal(Long fareId){
        FareResponse fareResponse=pricingClient.getFareById(fareId);
        Double baseFare=fareResponse.getBaseFare();
        Double taxesAbdFees=fareResponse.getTexesAndFees()
                 !=null ? fareResponse.getTexesAndFees() : 0;

        Double airlineFees=fareResponse.getAirlineFees()
                !=null ? fareResponse.getAirlineFees() : 0;

        return baseFare+taxesAbdFees+airlineFees;
    }
}
