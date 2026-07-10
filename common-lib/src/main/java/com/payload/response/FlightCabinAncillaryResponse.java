package com.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightCabinAncillaryResponse {

    private Long id;
    private  Long flightId;
    private Long cabinClassId;
    private AncillaryResponse ancillary;
    private Boolean available;
    private Integer maxQuantity;
    private Double price;
    private Boolean includedInFare;

}
