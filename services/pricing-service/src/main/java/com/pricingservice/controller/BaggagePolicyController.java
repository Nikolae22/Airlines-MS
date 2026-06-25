package com.pricingservice.controller;

import com.payload.request.BaggagePolicyRequest;
import com.payload.response.ApiResponse;
import com.payload.response.BaggagePolicyResponse;
import com.pricingservice.service.BaggagePolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baggage-policies")
public class BaggagePolicyController {

    private final BaggagePolicyService baggagePolicyService;

    @PostMapping
    public ResponseEntity<BaggagePolicyResponse> createBaggagePolicy(
            @Valid @RequestBody BaggagePolicyRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(baggagePolicyService.createBaggagePolicy(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyByFareId(
            @PathVariable(name = "fareId") Long fareId) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyByFareId(fareId));
    }
    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<BaggagePolicyResponse>> getBaggagePolicyByAirlineId(
            @PathVariable(name = "airlineId") Long airlineId) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyBiAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> updateBaggagePolicy(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody BaggagePolicyRequest request) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.updateBaggagePolicy(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBaggagePolicy(
            @PathVariable(name = "id") Long id) throws Exception {
        baggagePolicyService.deleteBaggagePolicy(id);
        return ResponseEntity.ok(new ApiResponse("Baggage policy deleted"));
    }


}
