package com.payload.request;

import com.payload.response.BaggagePolicyResponse;
import com.payload.response.FareRulesResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FareRequest {

    @NotBlank(message = "Fare Name is mandatory")
    private String name;

    @NotNull(message = "RbdCode is required")
    private Character rbdCode;

    @NotNull(message = "Flight id is required")
    private Long flightId;

    @NotNull(message = "Cabin class ID is required")
    private Long cabinClassId;

    @NotNull(message = "Base fare is required")
    @Positive
    private Double baseFare;

    private Double taxesAndFees;
    private Double airlineFees;
    private Double currentPrice;

    @Size(max = 100)
    private String fareLabel;

    //Seat benefits
    private Boolean extraSeatSpace;
    private Boolean preferredSeatChoice;
    private Boolean advanceSeatSelection;
    private Boolean guaranteedSeatTogether;

    //Boarding benefits
    private Boolean priorityBoarding;
    private Boolean priorityChecking;
    private Boolean fastTrackSecurity;

    // In flight benefits
    private Boolean complimentaryMeals;
    private Boolean premiumMealChoice;
    private Boolean inFlightInternet;
    private Boolean inFlightEntertainment;
    private Boolean complimentaryBeverages;

    // Flexibility benefits
    private Boolean freeDataChange;
    private Boolean partialRefund;
    private Boolean fullRefund;

    // Premium service Benefits
    private Boolean loungeAccess;
    private Boolean airportTransfer;

}
