package com.flightopsservice.controller;

import com.flightopsservice.service.FlightInstanceService;
import com.payload.request.FlightInstanceRequest;
import com.payload.response.ApiResponse;
import com.payload.response.FlightInstanceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-instances")
public class FlightInstanceController {

    private final FlightInstanceService flightInstanceService;


    @PostMapping
    public ResponseEntity<FlightInstanceResponse> createFlightInstance(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody FlightInstanceRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceService.createFlightInstance(airlineId,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> getFlightInstanceById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightInstanceService.getFlightInstanceById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FlightInstanceResponse>> getByAirlineId(
            @RequestHeader(name = "X-Airline-Id") Long id,
            @RequestParam(name = "departureAirportId", required = false) Long departureAirportId,
            @RequestParam(name = "arrivalAirportId", required = false) Long arrivalAirportId,
            @RequestParam(name = "flightId", required = false) Long flightId,
            @RequestParam(name = "onDate", required = false) LocalDate onDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(flightInstanceService.getByAirlineId(
                id, departureAirportId, arrivalAirportId, flightId, onDate, pageable
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @RequestParam Long id,
            @RequestBody @Valid FlightInstanceRequest request) throws Exception {
        return ResponseEntity.ok(flightInstanceService.updateFlightInstance(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstance(
            @PathVariable Long id) throws Exception {
        flightInstanceService.deleteFlightInstance(id);
        return ResponseEntity.ok(
                new ApiResponse("Flight instance Deleted")
        );
    }

}
