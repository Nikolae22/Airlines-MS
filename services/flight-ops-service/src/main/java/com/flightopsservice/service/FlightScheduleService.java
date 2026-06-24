package com.flightopsservice.service;

import com.payload.request.FlightScheduleRequest;
import com.payload.response.FlightScheduleResponse;

import java.util.List;

public interface FlightScheduleService {


    FlightScheduleResponse createFlightSchedule(Long airlineId,
                                                FlightScheduleRequest request) throws Exception;

    FlightScheduleResponse getFlightScheduleById(Long id) throws Exception;
    List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId);
    FlightScheduleResponse updateFlightSchedule(Long id,
                                                FlightScheduleRequest request) throws Exception;
    void deleteFlightSchedule(Long id) throws Exception;
}
