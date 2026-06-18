package com.locationservice.payload.request;

import com.embeddable.Address;
import com.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportRequest {

    @NotBlank(message = "IATA code is mandatory")
    @Size(min = 3, max = 3,message = "IATA code must be exactly 3ch")
    private String iataCode;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private ZoneId timeZone;

    @Valid
    private Address address;

    @NotNull(message = "City id is mandatory")
    private Long cityId;

    @Valid
    private GeoCode geoCode;

}
