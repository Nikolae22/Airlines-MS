package com.ancillaryservice.mapper;

import com.ancillaryservice.model.FlightCabinAncillary;
import com.payload.response.FlightCabinAncillaryResponse;
import com.payload.response.InsuranceCoverageResponse;

import java.util.List;

public class FlightCabinAncillaryMapper {

    public static FlightCabinAncillaryResponse toDTO(FlightCabinAncillary flightCabinAncillary,
                                                     List<InsuranceCoverageResponse> coverages){
        if (flightCabinAncillary == null) return  null;

        return FlightCabinAncillaryResponse.builder()
                .id(flightCabinAncillary.getId())
                .flightId(flightCabinAncillary.getFlightId())
                .cabinClassId(flightCabinAncillary.getCabinClassId())
                .ancillary(AncillaryMapper.toDTO(
                        flightCabinAncillary.getAncillary(),coverages
                ))
                .available(flightCabinAncillary.getAvailable())
                .maxQuantity(flightCabinAncillary.getMaxQuantity())
                .price(flightCabinAncillary.getPrice())
                .includedInFare(flightCabinAncillary.getIncludedInFare())
                .build();
    }
}
