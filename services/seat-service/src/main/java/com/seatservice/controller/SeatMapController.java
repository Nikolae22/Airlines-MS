package com.seatservice.controller;

import com.payload.request.SeatMapRequest;
import com.payload.response.ApiResponse;
import com.payload.response.SeatMapResponse;
import com.seatservice.service.SeatMapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat-maps")
public class SeatMapController {

    private final SeatMapService seatMapService;

    @PostMapping
    public ResponseEntity<SeatMapResponse> createSeatMap(
            @Valid @RequestBody SeatMapRequest request,
            @RequestHeader("X-Airline-Id") Long airlineId) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(seatMapService.createSeatMap(airlineId,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatMapResponse> getSeatMapById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(seatMapService.getSeatMapById(id));
    }

    @GetMapping("/cabin-class/{cabinClassId}")
    public ResponseEntity<SeatMapResponse> getSeatMapsByCabinClass(
            @PathVariable(name = "cabinClassId") Long cabinClassId){
        return ResponseEntity.ok(
                seatMapService.getSeatMapByCabinClass(cabinClassId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatMapResponse> updateSeatMap(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody SeatMapRequest request) throws Exception {
        return ResponseEntity.ok(seatMapService.updateSeatMap(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSeatMap(
            @PathVariable(name = "id") Long id) throws Exception {
        seatMapService.deleteSeatMap(id);
        return ResponseEntity.ok(new ApiResponse("Seat map deleted"));
    }
}
