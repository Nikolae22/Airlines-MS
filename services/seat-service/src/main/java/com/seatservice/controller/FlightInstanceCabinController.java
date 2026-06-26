package com.seatservice.controller;

import com.payload.request.FlightInstanceCabinRequest;
import com.payload.response.ApiResponse;
import com.payload.response.FlightInstanceCabinResponse;
import com.seatservice.service.FlightInstanceCabinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-instance-cabins")
public class FlightInstanceCabinController {

    public final FlightInstanceCabinService flightInstanceCabinService;

    @PostMapping
    public ResponseEntity<FlightInstanceCabinResponse> createFlightInstanceCabin(
            @Valid @RequestBody FlightInstanceCabinRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceCabinService.createFlightInstanceCabin(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> getFlightInstanceCabin(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceCabinService.getFlightInstanceCabinById(id));
    }

    @GetMapping("/flight-instance/{flightInstanceId}/cabin-class/{cabinClassId}")
    public ResponseEntity<FlightInstanceCabinResponse> getByFlightInstanceIdAndCabinClass(
            @PathVariable(name = "cabinClassId") Long cabinClassId,
            @PathVariable(name = "flightInstanceId") Long flightInstanceId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceCabinService.getByFlightInstanceIdAndCabinClassId(
                        flightInstanceId,cabinClassId
                ));
    }

    @GetMapping("/flight-instance/{flightInstanceId}")
    public ResponseEntity<Page<FlightInstanceCabinResponse>> getByFlightInstanceId(
            @PathVariable(name = "flightInstanceId") Long flightInstanceId, Pageable pageable){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceCabinService.getByFlightInstanceId(flightInstanceId,pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceCabinResponse> updateFlightInstanceCabin(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody FlightInstanceCabinRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceCabinService.updateFlightInstanceCabin(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstanceCabin(
            @PathVariable(name = "id") Long id) throws Exception {
     flightInstanceCabinService.deleteFlightInstanceCabin(id);
     return ResponseEntity.ok(new ApiResponse("FlightInstanceCabin deleted"));
    }


}
