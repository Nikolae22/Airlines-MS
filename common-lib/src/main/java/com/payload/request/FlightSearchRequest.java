package com.payload.request;

import com.enums.CabinClassType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightSearchRequest {

    private Long departureAirportId;
    private Long arrivalAirportId;

    @NotNull(message = "Departure date is required")
    private LocalDate departureDate;

    @Min(value = 1,message = "At least 1 lucky passenger")
    private Integer passengers;

    @NotNull(message = "Cabin class is required")
    private CabinClassType cabinClass;

    //filter parameter

    private List<Long> airlines;
    private Double minPrice;
    private Double maxPrice;

    private String departureTimeRange;
    private String arrivalTimeRange;

    private Integer maxDuration;
    private String sortBy;
    private String sortOrder;

}
