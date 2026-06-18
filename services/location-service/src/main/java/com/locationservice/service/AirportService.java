package com.locationservice.service;

import com.locationservice.payload.request.AirportRequest;
import com.locationservice.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest airportRequest) throws Exception;
    AirportResponse getAirportById(Long id) throws Exception;

    List<AirportResponse> getAllAirports();

    AirportResponse updateAirport(Long id,AirportRequest request) throws Exception;
    void deleteAirport(Long id) throws Exception;

    List<AirportResponse> getAirportByCityId(Long cityId);

}
