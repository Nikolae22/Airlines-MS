package com.payload.response;

import com.enums.AircraftStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AircraftResponse {

    private Long id;
    private Long currentAirportId;
    private String code;
    private String model;
    private String manufacturer;
    private Integer seatingCapacity;
    private Integer economySeats;
    private Integer premiumEconomySeats;
    private Integer businessSeats;
    private Integer firstClassSeats;
    private Integer totalSeats;
    private Integer rangeKm;
    private Integer cruisingSpeedKm;
    private Integer maxAltitudeFt;
    private Integer yearOfManufacture;
    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    private AircraftStatus status;
    private Boolean isAvailable;

    // campi calcolati
    private Boolean isOperational;
    private Boolean requiresMaintenance;

    private Long airlineId;
    private String airlineName;
    private String airlineIataCode;

    private Instant createdAt;
    private Instant updatedAt;
}
