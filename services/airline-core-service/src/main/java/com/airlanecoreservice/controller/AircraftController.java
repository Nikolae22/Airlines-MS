package com.airlanecoreservice.controller;

import com.airlanecoreservice.service.AircraftService;
import com.payload.request.AircraftRequest;
import com.payload.response.AircraftResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;


    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        AircraftResponse aircraft=aircraftService.createAircraft(request,userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> getAllAircrafts(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(aircraftService.listAllAircraftByOwner(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @RequestBody @Valid AircraftRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(aircraftService.updateAircraft(id,request,userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        aircraftService.deleteAircraft(id,ownerId);
        return ResponseEntity.noContent().build();
    }
}
