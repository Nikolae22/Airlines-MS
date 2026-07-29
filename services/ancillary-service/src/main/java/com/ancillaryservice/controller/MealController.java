package com.ancillaryservice.controller;

import com.ancillaryservice.services.MealService;
import com.payload.request.MealRequest;
import com.payload.response.ApiResponse;
import com.payload.response.MealResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/melas")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PostMapping
    public ResponseEntity<MealResponse> createMeal(
            @Valid @RequestBody MealRequest request,
    @RequestHeader("X-User-Id")Long userId ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mealService.createMeal(userId,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealResponse> getMealById(@PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(mealService.getMealById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<List<MealResponse>> getMealsByAirlineId(
            @RequestHeader("X-User-Id")Long userId){
        return ResponseEntity.ok(mealService.getByAirlineId(userId));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<MealResponse> updateMealAvailability(
            @PathVariable(name = "id") Long id,
            @RequestParam Boolean available) throws Exception {
        return ResponseEntity.ok(mealService.updateAvailability(id,available));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealResponse> updateMeal(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody MealRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(mealService.updateMeal(userId,id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMeal(
            @PathVariable(name = "id") Long id) throws Exception {
        mealService.deleteMeal(id);
        return ResponseEntity.ok(new ApiResponse("Meal deleted"));
    }
}
