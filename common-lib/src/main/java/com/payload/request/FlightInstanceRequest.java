package com.payload.request;

import com.enums.FlightStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightInstanceRequest {

    @NotNull(message = "Flight id is required")
    private Long flightId;

    private Long scheduleId;

    private Long departureAirportId;
    private Long arrivalAirportId;

    @NotNull(message = "Departire time required")
    private LocalDateTime departureDateTime;
    @NotNull(message = "Arrival time required")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Total seats is required")
    @Positive
    private Integer totalSeats;

    @PositiveOrZero
    private Integer availableSeats;

    private FlightStatus status;

    private Integer minAdvancedBookingDays;
    private Integer maxAdvancedBookingDays;
    private Boolean isActive;
}
