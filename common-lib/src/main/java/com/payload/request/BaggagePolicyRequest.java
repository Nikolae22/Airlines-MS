package com.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaggagePolicyRequest {

    @NotBlank(message = "Policy name is required")
    private String name;

    @NotNull(message = "Fare id is required")
    private Long fareId;

    private String description;

    @PositiveOrZero
    private Double cabinBaggageMaxWeight;
    @PositiveOrZero
    private Integer cabinBaggagePieces;

    @PositiveOrZero
    private Double cabinBaggageWeightPerPiece;

    private Integer cabinBaggageMaxDimension;


    //checkin baggage
    @PositiveOrZero
    private Double checkInBaggageMaxWeight;
    @PositiveOrZero
    private Integer checkInBaggagePieces;
    @PositiveOrZero
    private Double checkInBaggageWeightPerPieces;

    @PositiveOrZero
    private Integer freeCheckedBagsAllowance;

    //benefits
    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowance;


}
