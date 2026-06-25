package com.pricingservice.controller;

import com.payload.request.FareRulesRequest;
import com.payload.response.ApiResponse;
import com.payload.response.FareRulesResponse;
import com.pricingservice.service.FareRulesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fare-rules")
public class FareRuleController {

    private final FareRulesService fareRulesService;


    @PostMapping
    public ResponseEntity<FareRulesResponse> createFare(@Valid @RequestBody FareRulesRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(fareRulesService.createFareRules(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<FareRulesResponse> getFareRulesById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<FareRulesResponse> getFareRulesByFareId(
            @PathVariable(name = "fareId") Long fareId) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesByFareId(fareId));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FareRulesResponse>> getFareRulesByAirlineId(
            @PathVariable(name = "airlineId") Long airlineId) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesByAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareRulesResponse> updateFareRules(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody FareRulesRequest request) throws Exception {
        return ResponseEntity.ok(fareRulesService.updateFareRules(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFareRulesById(
            @PathVariable(name = "id") Long id) throws Exception {
        fareRulesService.deleteFareRules(id);
        return ResponseEntity.ok(new ApiResponse("FareRules deleted succ"));
    }
}
