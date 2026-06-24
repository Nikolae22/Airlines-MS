package com.flightopsservice.controller;

import com.flightopsservice.service.FlightScheduleService;
import com.payload.request.FlightScheduleRequest;
import com.payload.response.ApiResponse;
import com.payload.response.FlightScheduleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;


    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader(name = "X-Airline-Id") Long airlineId,
            @Valid @RequestBody FlightScheduleRequest request) throws Exception {
        //todo watch for airline id
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightScheduleService.createFlightSchedule(airlineId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleById(id));
    }

    @GetMapping
    public ResponseEntity<?> getFlightSchedules(
            @RequestHeader(name = "X-User-Id") Long airlineId) {
        return ResponseEntity.ok(
                flightScheduleService.getFlightScheduleByAirline(airlineId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody FlightScheduleRequest request) throws Exception {
        return ResponseEntity.ok(flightScheduleService.updateFlightSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightSchedule(
            @PathVariable(name = "id") Long id) throws Exception {
        flightScheduleService.deleteFlightSchedule(id);
        return ResponseEntity.ok(new ApiResponse("Flight schedule deleted"));
    }
}