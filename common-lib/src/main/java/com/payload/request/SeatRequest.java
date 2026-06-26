package com.payload.request;

import com.enums.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatRequest {

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotNull(message = "Seat row is required")
    private Integer seatRow;

    private Character columnLetter;

    @NotNull(message = "Seat type required")
    private SeatType seatType;

    private Long cabinClassId;

    private Boolean isAvailable;
    private Boolean isBlocked;
    private Boolean isEmergencyExit;
    private Boolean isActive;

    private Double basePrice;
    private Double premiumSuperCharge;

    private Boolean hasExtraLegRoom;
    private Boolean hasPowerOutlet;
    private Boolean hasTvScreen;
    private Boolean hasExtraWidth;

    private Integer seatPitch;
    private Integer seatWidth;

}
