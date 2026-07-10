package com.ancillaryservice.controller;

import com.ancillaryservice.services.FlightMealService;
import com.payload.request.FlightMealRequest;
import com.payload.response.ApiResponse;
import com.payload.response.FlightMealResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-meals")
public class FlightMealController {

    private final FlightMealService flightMealService;

    @PostMapping
    public ResponseEntity<FlightMealResponse> createFlightMeal(
            @Valid @RequestBody FlightMealRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightMealService.createFlightMeal(request));
    }

    @PostMapping("/price/total")
    public ResponseEntity<Double> calculateMealPrice(
            @RequestBody List<Long> requests){
        double response=flightMealService.calculatePrice(requests);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightMealResponse> getFlightMealById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(flightMealService.getFlightMealById(id));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FlightMealResponse>> geatMealsByFlightId(
            @PathVariable(name = "flightId") Long flightId){
        return ResponseEntity.ok(flightMealService.getByFlightId(flightId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightMealResponse>> getMealsByIds(
            @RequestParam List<Long> ids){
        return ResponseEntity.ok(flightMealService.getAllByIds(ids));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<FlightMealResponse> updateFlightMealAvailability(
            @PathVariable(name = "id")Long id,
            @RequestParam Boolean available) throws Exception {
        return ResponseEntity
                .ok(flightMealService.updateFlightMealAvailability(id,available));
    }

    @DeleteMapping("/{d}")
    public ResponseEntity<ApiResponse> deleteFlightMeal(
            @PathVariable(name = "id") Long id) throws Exception {
        flightMealService.deleteFlightMeal(id);
        return ResponseEntity.ok(new ApiResponse("Flight meal deleted"));

    }
}
