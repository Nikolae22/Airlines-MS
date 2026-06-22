package com.payload.response;

import com.embeddable.Support;
import com.enums.AirlineStatus;
import com.payload.dto.UserDTO;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineResponse {

    private Long id;
    private String iataCode;
    private String icaoCode;

    private String name;
    private String alias;



    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private Long ownerId;
    private UserDTO owner;
    private Long updatedById;

    private CityResponse headquarterCity;

    private Instant createdAt;
    private Instant updatedAt;

    @Embedded
    private Support support;
}
