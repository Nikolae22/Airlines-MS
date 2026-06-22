package com.payload.request;

import com.enums.AirlineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineRequest {

    @NotBlank(message = "IATA code is mandatory")
    @Size(min = 2,max = 2,message = "IATA codem ust be exactly 2ch")
    private String iataCode;

    @NotBlank(message = "ICAO code is mandatory")
    @Size(min = 3,max = 3,message = "ICAO code msut be exactly 3ch")
    private String icaoCode;

    @NotBlank
    private String name;

    private String alias;

    private String logoUrl;
    private String website;
    private AirlineStatus status;
    private String alliance;
    private Long headquartersCityId;
    private String supportEmail;
    private String supportPhone;
    private String supportHours;

}
