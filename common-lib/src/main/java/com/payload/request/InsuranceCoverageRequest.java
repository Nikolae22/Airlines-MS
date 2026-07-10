package com.payload.request;


import com.enums.CoverageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceCoverageRequest {


    @NotNull(message = "Ancillary id is required")
    private Long ancillaryId;


    @NotNull(message = "Coverage type is required")
    private CoverageType coverageType;

    @NotBlank(message = "Coverage name is required")
    @Size(max = 200, message = "Max name 200ch")
    private String name;


    @Size(max = 1000, message = "Max description 1000ch")
    private String description;

    @NotNull(message = "Coverage amount is required")
    @PositiveOrZero(message = "Cannot be zero or negative")
    private Double coverageAmount;

    private Boolean isFlat;

    @Size(max = 500, message = "Claim condition cannot be longer that 500ch")
    private String claimCondition;

    @Size(max = 100, message = "Max 100ch")
    private String emergencyContanct;

    private Integer displayOrder;
    private Boolean active;
}
