package com.flightopsservice.controller;

import com.enums.FlightStatus;
import com.flightopsservice.service.FlightService;
import com.payload.request.FlightRequest;
import com.payload.response.FlightResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FLightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFLight(@Valid @RequestBody FlightRequest request,
                                                       @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightService.createFlight(userId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>> getFlightByAirline(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(name = "departureAirportId", required = false) Long departureAirportId,
            @RequestParam(name = "arrivalAirportId", required = false) Long arrivalAirportId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(flightService.getFlightsByAirline(
                userId, departureAirportId, arrivalAirportId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @RequestBody @Valid FlightRequest request) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam FlightStatus status) throws Exception {
        return ResponseEntity.ok(flightService.changeStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) throws Exception {
        flightService.deleteFlight(id, userId);
        return ResponseEntity.noContent().build();
    }


}
