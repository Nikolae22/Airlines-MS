package com.payload.request;

import com.payload.response.SeatResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatMapRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Positive
    @NotNull(message = "total is required")
    private Integer totalRows;

    @Positive
    @NotNull(message = "left seats is required")
    private Integer leftSeatsPerRow;
    @Positive
    @NotNull(message = "right seats is required")
    private Integer rightSeatsPerRow;

    private Long cabinClassId;
}
