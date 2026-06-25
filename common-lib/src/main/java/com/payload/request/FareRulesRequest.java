package com.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FareRulesRequest {

    @NotBlank(message = "Rule name is required")
    private String ruleName;

    @NotNull(message = "Fare id is required")
    private Long fareId;
    private Long airlineId;
    private Boolean isRefundable;

    @PositiveOrZero(message = "Change fee must be positivo ore zero")
    private Double changeFee;

    @PositiveOrZero(message = "Cancelation fee must be positivo ore zero")
    private Double cancellationFee;

    @PositiveOrZero(message = "Refund deadline must be positivo ore zero")
    private Integer refundDeadlineDays;

    @PositiveOrZero(message = "Change deadline must be positivo ore zero")
    private Integer changeDeadlineHours;
    private Boolean isChangeable;


}
