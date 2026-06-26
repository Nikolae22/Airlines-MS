package com.seatservice.mapper;

import com.payload.response.FlightInstanceCabinResponse;
import com.seatservice.model.FlightInstanceCabin;

import java.util.stream.Collectors;

public class FlightInstanceCabinMapper {

    public static FlightInstanceCabinResponse toDTO(FlightInstanceCabin fic) {
        if (fic == null) return null;
        return FlightInstanceCabinResponse.builder()
                .id(fic.getId())
                .flightInstanceId(fic.getFlightInstanceId())
                .cabinClassType(fic.getCabinClass().getName())
                .cabinClass(CabinClassMapper.toDTO(fic.getCabinClass(),
                        fic.getCabinClass().getSeatMap()))
                .seats(fic.getSeats() !=null ?
                        fic.getSeats()
                                .stream()
                                .map(SeatInstanceMapper::toDTO).collect(Collectors.toList()) :null)
                .seatMap(fic.getCabinClass() != null && fic.getCabinClass().getSeatMap() != null ?
                        SeatMapMapper.toSimpleResponse(fic.getCabinClass().getSeatMap()) : null)
                .totalSeats(fic.getTotalSeats())
                .bookedSeats(fic.getBookedSeats())
                .availableSeats(fic.getAvailableSeats())
                .build();
    }
}
