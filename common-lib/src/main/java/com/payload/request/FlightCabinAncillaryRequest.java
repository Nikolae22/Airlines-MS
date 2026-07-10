package com.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightCabinAncillaryRequest {

    @NotNull(message = "Flight id is required")
    private Long flightId;

    @NotNull(message = "Cabin class id is required")
    private Long cabinClassId;

    @NotNull(message = "AncillaryId is required")
    private Long ancillaryId;

    @NotNull(message = "Availability status is required")
    private Boolean available;

    private Integer maxQuantity;

    private Double price;

    @NotNull(message = "Included in fare status is required")
    private Boolean includedInFare;

}
