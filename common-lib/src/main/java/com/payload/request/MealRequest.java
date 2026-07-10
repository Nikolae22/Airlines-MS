package com.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealRequest {

    @NotBlank(message = "Meal code is required")
    private String code;

    @NotBlank(message = "Meal name is required")
    private String name;

    @NotBlank(message = "Meal type is required")
    @Size(max = 50, message = "Meal type must not exceed 5ch")
    private String mealType;

    @Size(max = 100,message = "Dietary restriction max 100ch")
    private String dietaryRestriction;

    @Size(max = 2000,message = "Ingredients max 2000ch")
    private String ingredients;

    @Size(max = 500, message = "Image url max 500ch")
    private String imageUrl;


    private Boolean available;

    private Boolean requiresAdvanceBooking;

    private Integer advanceBookingHours;

    private Integer displayOrder;
}
