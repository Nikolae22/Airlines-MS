package com.ancillaryservice.services;

import com.payload.request.FlightMealRequest;
import com.payload.response.FlightMealResponse;

import java.util.List;

public interface FlightMealService {

    FlightMealResponse createFlightMeal(FlightMealRequest request) throws Exception;
    FlightMealResponse getFlightMealById(Long id) throws Exception;
    List<FlightMealResponse> getByFlightId(Long flightId);
    List<FlightMealResponse> getAllByIds(List<Long> ids);
    FlightMealResponse updateFlightMeal(Long id, FlightMealRequest request) throws Exception;
    void deleteFlightMeal(Long id) throws Exception;
    FlightMealResponse updateFlightMealAvailability(Long id,Boolean availability) throws Exception;
    Double calculatePrice(List<Long> mealIds);
}
