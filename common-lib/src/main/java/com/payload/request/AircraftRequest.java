package com.payload.request;

import com.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AircraftRequest {

    @NotBlank(message = "Aircraft code is required")
    private String code;

    @NotBlank(message = "Is required")
    private String model;

    @NotBlank(message = "Is required")
    private String manufacture;

    @NotNull(message = "Is required")
    @Positive(message = "Seating capacity must be positive")
    private Integer seatingCapacity;

    @Positive(message = "Must be Positive")
    private Integer economySeats;

    @Positive(message = "Must be positive")
    private  Integer premiumEconomySeats;

    @Positive(message = "Must be positive")
    private Integer businessSeats;

    @Positive(message = "First class seats must be positive ")
    private Integer firstClassSeats;

    @Positive(message = "Range must be positive")
    private Integer rangeKm;

    @Positive(message = "Mustb e positive")
    private Integer cruisingSpeedKmh;

    @Positive(message = "Must be positive")
    private Integer maxAltitudeFt;

    @Positive(message = "Must be positive")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @NotNull(message = "Is required")
    private AircraftStatus status;

    @NotNull(message = "Is required")
    private Boolean isAvailable;

    private Long currentAirportId;
}
