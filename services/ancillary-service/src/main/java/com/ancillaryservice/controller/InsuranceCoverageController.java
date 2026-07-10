package com.ancillaryservice.controller;

import com.ancillaryservice.model.InsuranceCoverage;
import com.ancillaryservice.services.InsuranceCoverageService;
import com.payload.request.InsuranceCoverageRequest;
import com.payload.response.ApiResponse;
import com.payload.response.InsuranceCoverageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance-coverages")
@RequiredArgsConstructor
public class InsuranceCoverageController {

    private final InsuranceCoverageService insuranceCoverageService;

    @PostMapping
    public ResponseEntity<InsuranceCoverageResponse> createCoverage(
            @Valid @RequestBody InsuranceCoverageRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(insuranceCoverageService.createCoverage(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceCoverageResponse> updateCoverage(
            @PathVariable(name = "id")Long id,
            @RequestBody InsuranceCoverageRequest request) throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.updateCoverage(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCoverage(
            @PathVariable(name = "id") Long id) throws Exception {
        insuranceCoverageService.deleteCoverage(id);
        return ResponseEntity.ok(new ApiResponse("Coverage deleted"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceCoverageResponse> getCoverageById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.getCoverage(id));
    }

    @GetMapping
    public ResponseEntity<List<InsuranceCoverageResponse>> getAllCoverages(){
        return ResponseEntity.ok(insuranceCoverageService.getAllCoverages());
    }

    @GetMapping("/ancillary/{ancillaryId}")
    public ResponseEntity<List<InsuranceCoverageResponse>> getCoverageByAncillaryId(
            @PathVariable(name = "ancillaryId") Long id){
        return ResponseEntity.ok(insuranceCoverageService.getCoverageByAncillaryId(id));
    }

    @GetMapping("/ancillary/{ancillaryId}/actve")
    public ResponseEntity<List<InsuranceCoverageResponse>> getActiveCoverageByAncillaryId(
            @PathVariable(name = "ancillaryId") Long id){
        return ResponseEntity.ok(insuranceCoverageService.getActiveCoverageByAncillaryId(id));
    }





}
