package com.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightInstanceCabinRequest {

    @NotNull
    private Long flightId;

    @NotNull(message = "Flight instance id is required")
    private Long flightInstanceId;

    @NotNull
    private Long cabinClassId;

    @NotNull
    @Positive
    private Double baseFare;

    @NotNull
    @PositiveOrZero
    private Double taxesAndFees;

    @NotNull
    @PositiveOrZero
    private Double airlineFees;

    private Double currentPrice;
    private Boolean isActive;


}
