package com.seatservice.controller;

import com.enums.CabinClassType;
import com.payload.request.CabinClassRequest;
import com.payload.response.ApiResponse;
import com.payload.response.CabinClassResponse;
import com.seatservice.model.CabinClass;
import com.seatservice.service.CabinClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cabin-classes")
public class CabinClassController {

    private final CabinClassService cabinClassService;

    @PostMapping
    public ResponseEntity<CabinClassResponse> createCabinClass(
            @Valid @RequestBody CabinClassRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cabinClassService.createCabinClass(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabinClassResponse> getCabinClassById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(cabinClassService.getCabinClassById(id));
    }

    @GetMapping("/aircraft/{id}/name/{cabinClass}")
    public ResponseEntity<CabinClassResponse> getCabinClassByAircraftIdAndName(
            @PathVariable(name = "cabinClass") CabinClassType cabinClass,
            @PathVariable(name = "id") Long id){
        return ResponseEntity.ok(cabinClassService.getByAircraftIdAndName(id,cabinClass));
    }


    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<List<CabinClassResponse>> getCabinClassByAircraftId(
            @PathVariable(name = "aircraftId") Long aircraftId){
        return ResponseEntity.ok(cabinClassService.getCabinClassesByAircraftId(aircraftId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabinClassResponse> updateCabinClass(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody CabinClassRequest request) throws Exception {
        return ResponseEntity.ok(cabinClassService.updateCabinClass(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCabinClass(
            @PathVariable(name = "id") Long id) throws Exception {
        cabinClassService.deleteCabinClass(id);
        return ResponseEntity.ok(new ApiResponse("Cabin class deleted"));
    }


}
