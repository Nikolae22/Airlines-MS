package com.ancillaryservice.controller;

import com.ancillaryservice.services.AncillaryService;
import com.payload.request.AncillaryRequest;
import com.payload.response.AncillaryResponse;
import com.payload.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ancillaries")
@RequiredArgsConstructor
public class AncillaryController {

    private final AncillaryService ancillaryService;


    @PostMapping
    public ResponseEntity<AncillaryResponse> createAncillary(
            @RequestBody @Valid AncillaryRequest request,
            @RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ancillaryService.createAncillary(userId,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AncillaryResponse> getById(
            @PathVariable(name = "id") Long id) throws Exception {
        return ResponseEntity.ok(ancillaryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AncillaryResponse>> getByAirlineId(
            @RequestHeader("X-User-Id") Long userId
    ){
        return ResponseEntity.ok(ancillaryService.getByAirlineId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AncillaryResponse> update(
            @PathVariable(name = "id")Long id,
            @Valid @RequestBody AncillaryRequest request) throws Exception {
        return ResponseEntity.ok(ancillaryService.updateAncillary(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(
            @PathVariable(name = "id")Long id) throws Exception {
        ancillaryService.deleteAncillary(id);
        return ResponseEntity.ok(new ApiResponse("Ancillary deleted"));
    }

}
