package com.seatservice.mapper;

import com.enums.SeatAvailabilityStatus;
import com.payload.response.SeatInstanceResponse;
import com.seatservice.model.SeatInstance;

public class SeatInstanceMapper {

    public static SeatInstanceResponse toDTO(SeatInstance si){
        return SeatInstanceResponse.builder()
                .id(si.getId())
                .flightId(si.getFlightId())
                .seatId(si.getSeat() !=null ? si.getSeat().getId() : null)
                .seatNumber(si.getSeat() !=null ? si.getSeat().getSeatNumber() : null)
                .seatType(si.getSeat() !=null ? si.getSeat().getSeatType().name() : null)
                .seatPosition(si.getSeat() !=null ? si.getSeat().getFullPosition() : null)
                .seat(SeatMapper.toDTO(si.getSeat()))
                .status(si.getStatus())
                .flightInstanceId(si.getFlightInstanceId())
                .flightCabinId(si.getFlightInstanceCabin() !=null ? si.getFlightInstanceCabin().getId() : null)
                .fare(si.getFare())
                .price(si.getPremiumSuperCharge())
                .version(si.getVersion())
                .createdAt(si.getCreatedAt())
                .updatedAt(si.getUpdatedAt())
                .isAvailable(si.isAvailable())
                .isBooked(si.isBooked())
                .isOccupied(si.getStatus() == SeatAvailabilityStatus.OCCUPIED)
//                .seatCharacteristics(si.getSeat() !=null ? si.getSeat().getSeatCharacteristics(): null)
                .build();

    }
}
