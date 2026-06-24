package com.flightopsservice.service;

import com.enums.FlightStatus;
import com.payload.request.FlightRequest;
import com.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FlightService {

    FlightResponse createFlight(Long airlineId, FlightRequest request) throws Exception;
    Page<FlightResponse> getFlightsByAirline(Long airlineId,
                                             Long departureAirportId,
                                             Long arrivalAirportId,
                                             Pageable pageable);

    FlightResponse getFlightById(Long id) throws Exception;
    FlightResponse updateFlight(Long id, FlightRequest request) throws Exception;
    FlightResponse changeStatus(Long id, FlightStatus status) throws Exception;
    void deleteFlight(Long airlineId,Long id) throws Exception;

}
