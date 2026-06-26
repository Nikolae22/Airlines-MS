package com.seatservice.service;

import com.payload.request.FlightInstanceCabinRequest;
import com.payload.response.FlightInstanceCabinResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightInstanceCabinService {

    FlightInstanceCabinResponse createFlightInstanceCabin(FlightInstanceCabinRequest request) throws Exception;
    FlightInstanceCabinResponse getFlightInstanceCabinById(Long id) throws Exception;
    Page<FlightInstanceCabinResponse> getByFlightInstanceId(Long id, Pageable pageable);
    FlightInstanceCabinResponse getByFlightInstanceIdAndCabinClassId(Long flightInstanceId, Long cabinClassId);
    FlightInstanceCabinResponse updateFlightInstanceCabin(Long id, FlightInstanceCabinRequest request) throws Exception;
    void deleteFlightInstanceCabin(Long id) throws Exception;

}
