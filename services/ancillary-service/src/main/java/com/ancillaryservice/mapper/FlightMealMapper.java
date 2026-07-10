package com.ancillaryservice.mapper;

import com.ancillaryservice.model.FlightMeal;
import com.payload.response.FlightMealResponse;

public class FlightMealMapper {

    public static FlightMealResponse toDto(FlightMeal flightMeal){
        if (flightMeal==null) return null;
        return FlightMealResponse.builder()
                .id(flightMeal.getId())
                .flightId(flightMeal.getFlightId())
                .meal(MealMapper.toDTO(flightMeal.getMeal()))
                .available(flightMeal.getAvailable())
                .price(flightMeal.getPrice())
                .displayOrder(flightMeal.getDisplayOrder())
                .build();
    }
}
