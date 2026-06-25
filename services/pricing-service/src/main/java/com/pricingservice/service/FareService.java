package com.pricingservice.service;

import com.payload.request.FareRequest;
import com.payload.response.FareResponse;
import com.pricingservice.model.Fare;

import java.util.List;
import java.util.Map;

public interface FareService {

    FareResponse createFare(FareRequest request) throws Exception;
    FareResponse getFareById(Long id) throws Exception;
    List<FareResponse> getFaresByFlightIdAndCabinClassId(
            Long flightId, Long cabinClassId);
    FareResponse updateFare(Long id, FareRequest request) throws Exception;
    void deleteFare(Long id) throws Exception;
    List<Fare> getFares();

    Map<Long,FareResponse> getLowestFarePerFlight(
            List<Long> flightsIds,Long cabinClassId);

    Map<Long,FareResponse> getFaresByIds(List<Long> ids);
}
