package com.airlanecoreservice.model;

import com.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false,length = 50)
    private String manufacturer;

    private Integer economySeats=0;

    private Integer premiumEconomySeats=0;

    private Integer businessSeats=0;

    private Integer firstClassSeats=0;

    private Integer cruisingSpeedKmh;

    private Integer rangeKm;

    private  Integer maxAltitudeFt;

    private Integer yearOfManufacture;

    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status",nullable = false,length = 20)
    private AircraftStatus status=AircraftStatus.ACTIVE;

    private Boolean isAvailable=true;

    private Integer seatingCapacity;

    @ManyToOne
    private Airline airline;

    private Long currentAirportId;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    public Integer getTotalSeats(){
        return economySeats+premiumEconomySeats+businessSeats+firstClassSeats;
    }

    public boolean isOperational(){
        return AircraftStatus.ACTIVE.equals(status)&&
                Boolean.TRUE.equals(isAvailable);
    }

    public boolean requiresMaintenance(){
        return nextMaintenanceDate !=null && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }




}
