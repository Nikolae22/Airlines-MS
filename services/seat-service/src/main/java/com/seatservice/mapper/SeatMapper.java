package com.seatservice.mapper;

import com.payload.response.SeatResponse;
import com.seatservice.model.Seat;

public class SeatMapper {

    public static SeatResponse toDTO(Seat seat){
        if (seat ==null) return null;
        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatRow(seat.getSeatRow())
                .columnLetter(seat.getColumnLetter())
                .seatType(seat.getSeatType())
                .isAvailable(seat.getIsAvailable())
                .isBLocked(seat.getIsBlocked())
                .isActive(seat.getIsActive())
                .basePrice(seat.getBasePrice())
                .premiumSurcharge(seat.getPremiumSuperCharge())
                .totalPrice(seat.getBasePrice())
                .hasExtraLegRoom(seat.getHasExtraLegRoom())
                .hasPowerOutlet(seat.getHasPowerOutlet())
                .hasTvScreen(seat.getHasTvScreen())
                .hasExtraWidth(seat.getHasExtraWidth())
                .seatPitch(seat.getSeatPitch())
                .seatWidth(seat.getSeatWidth())
                .seatMapId(seat.getSeatMap() !=null ? seat.getSeatMap().getId() : null)
                .seatMapName(seat.getSeatMap() !=null ? seat.getSeatMap().getName():null)
                .cabinClassId(seat.getSeatMap() !=null ? seat.getCabinClass().getId() : null)
                .cabinClassName(seat.getSeatMap() !=null ? seat.getCabinClass().getName().toString() : null)
                .createdAt(seat.getCreatedAt())
                .updatedAt(seat.getUpdatedAt())
                .createdBy(seat.getCreatedBy())
                .updatedBy(seat.getCreatedBy())
//                .isBookable(seat.isBookable())
                .fullPosition(seat.getFullPosition())
                .build();
    }


}
