package com.pricingservice.controller;

import com.payload.request.FareRequest;
import com.payload.response.ApiResponse;
import com.payload.response.FareResponse;
import com.pricingservice.service.FareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fares")
public class FareController {

    private final FareService fareService;

    @PostMapping
    public ResponseEntity<FareResponse> createFare(
            @Valid @RequestBody FareRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fareService.createFare(request));
    }

    @GetMapping
    public ResponseEntity<?> getFares(){
        return ResponseEntity.status(HttpStatus.OK).body(fareService.getFares());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FareResponse> getFareById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(fareService.getFareById(id));
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<List<FareResponse>> getFaresByFlightAndCabinClass(
        @PathVariable(name = "flightId") Long flightId,
        @PathVariable(name = "cabinClassId") Long cabinClassId
        ){
        return ResponseEntity.ok(fareService.getFaresByFlightIdAndCabinClassId(
                flightId,cabinClassId));
    }

    //se usiamo get non possiam oil RequestBody
    @PostMapping("/batch-by-ids")
    public ResponseEntity<Map<Long,FareResponse>> getFaresByIds(@RequestBody List<Long> ids){
        return ResponseEntity.ok(fareService.getFaresByIds(ids));
    }

    @PostMapping("/search")
    public ResponseEntity<Map<Long,FareResponse>> getLowestFaresForFlight(
            @RequestBody List<Long> flightsIds,
            @RequestParam(name = "cabinClassId") Long cabinClassId){
        Map<Long,FareResponse> res=fareService.getLowestFarePerFlight(flightsIds,cabinClassId);
        System.out.println("Search for response "+res.toString());
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareResponse> updateFare(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody FareRequest request) throws Exception {
        return ResponseEntity.ok(fareService.updateFare(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFare(
            @PathVariable(name = "id") Long id) throws Exception {
        fareService.deleteFare(id);
        return ResponseEntity.ok(new ApiResponse("Fare deleted"));
    }
}
