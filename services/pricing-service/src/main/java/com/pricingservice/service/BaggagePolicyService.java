package com.pricingservice.service;

import com.payload.request.BaggagePolicyRequest;
import com.payload.response.BaggagePolicyResponse;

import java.util.List;

public interface BaggagePolicyService {

    BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception;
    BaggagePolicyResponse getBaggagePolicyById(Long id) throws Exception;
    BaggagePolicyResponse getBaggagePolicyByFareId(Long faireId);
    List<BaggagePolicyResponse> getBaggagePolicyBiAirlineId(Long airlineId);
    BaggagePolicyResponse updateBaggagePolicy(Long id,BaggagePolicyRequest request) throws Exception;
    void deleteBaggagePolicy(Long id) throws Exception;
}
