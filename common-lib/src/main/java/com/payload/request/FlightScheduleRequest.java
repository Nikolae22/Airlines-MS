package com.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightScheduleRequest {

    @NotNull(message = "Flight id is required")
    private Long flightId;

    @NotNull(message = "Departure time is required")
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is reuired")
    private LocalTime arrivalTime;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private List<DayOfWeek> operatingDays;

    private Boolean isActive;


}
