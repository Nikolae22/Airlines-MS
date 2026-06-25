package com.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaggagePolicyResponse {

    private Long id;

    private String name;
    private String description;

    //cabin baggage
    private Double cabinBaggageMaxWeight;
    private Integer cabinBaggagePieces;
    private Double cabinBaggageWeightPerPieces;
    private Integer cabinBaggageMaxDimension;


    //checkin baggage
    private Double checkInBaggageMaxWeight;
    private Integer checkInBaggagePieces;
    private Double checkInBaggageWeightPerPieces;
    private Integer freeCheckedBagsAllowance;

    //benefits
    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowance;

    //Relationship
    private Long airlineId;
    private Long fareId;

    //audit
    private Instant createdAt;
    private Instant updatedAt;
}
