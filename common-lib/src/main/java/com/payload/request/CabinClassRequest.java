package com.payload.request;

import com.enums.CabinClassType;
import com.payload.response.SeatMapResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CabinClassRequest {

    @NotNull(message = "Name is required")
    private CabinClassType name;

    @NotBlank(message = "Code is required")
    private String code;

    private String description;

    @NotNull(message = "Aircraft Id is required")
    private Long aircraftId;

    private Integer displayOrder;
    private Boolean isActive;
    private Boolean isBookable;
    private Integer typicalSeatPitch;
    private Integer typicalSeatWidth;
    private String seatType;
}
