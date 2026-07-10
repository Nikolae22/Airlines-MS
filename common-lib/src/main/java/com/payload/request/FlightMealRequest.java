package com.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightMealRequest {

    @NotNull(message = "Flight id is required")
    private Long flightId;

    @NotNull(message = "Meal id is required")
    private Long mealId;
    private Boolean available;

    @DecimalMin(value = "0.0",message = "Price cannot be negative")
    private Double price;

    @Min(value = 0,message = "Display otder cannot be negative")
    private Integer displayOrder;
}
