package com.ancillaryservice.controller;

import com.ancillaryservice.model.FlightCabinAncillary;
import com.ancillaryservice.services.FlightCabinAncillaryService;
import com.enums.AncillaryType;
import com.payload.request.FlightCabinAncillaryRequest;
import com.payload.response.ApiResponse;
import com.payload.response.FlightCabinAncillaryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-cabin-ancillaries")
public class FlightCabinAncillaryController {


    private final FlightCabinAncillaryService service;

    @PostMapping
    public ResponseEntity<FlightCabinAncillaryResponse> createFlightCabinAncillary(
            @Valid @RequestBody FlightCabinAncillaryRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightCabinAncillaryResponse> getById(
            @PathVariable(name = "id")Long id) throws Exception {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}")
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getAllByFlightAndCabinClass(
            @PathVariable(name = "flightId") Long flightId,
            @PathVariable(name = "cabinClassId") Long cabinClassId){
        return ResponseEntity.ok(service.getByFlightAndCabinClass(
                flightId,cabinClassId));
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}/type/{type}")
    public ResponseEntity<FlightCabinAncillaryResponse> getByFlightAndCabinClassAndType(
            @PathVariable(name = "flightId") Long flightId,
            @PathVariable(name = "cabinClassId") Long cabinClassId,
            @PathVariable(name = "type")AncillaryType type) {
        return ResponseEntity.ok(
                service.getByFlightIdAndCabinClassIdAndType(flightId,cabinClassId,type)
        );
    }

    @GetMapping("/flight/{flightId}/cabin/{cabinClassId}/type/{type}/all")
    public ResponseEntity<List<FlightCabinAncillaryResponse>> getAllByFlightAndCabinClassAndType(
            @PathVariable(name = "flightId") Long flightId,
            @PathVariable(name = "cabinClassId") Long cabinClassId,
            @PathVariable(name = "type")AncillaryType type) {
        return ResponseEntity.ok(
                service.getAllByFlightIdAndCabinClassIdAndType(flightId,cabinClassId,type)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightCabinAncillaryResponse> update(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody FlightCabinAncillaryRequest request) throws Exception {
        return ResponseEntity.ok(service.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable(name = "id") Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse("Flight cabin ancillary deleted succ"));
    }

    @PutMapping("/price/total")
    public ResponseEntity<?> calculateAncillariesPrice(
            @RequestBody List<Long> flightCabinAncillaryIds){
        return ResponseEntity.ok(service.calculateAncillaryPrice(flightCabinAncillaryIds));
    }
}
