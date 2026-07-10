package com.ancillaryservice.controller;

import com.payload.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<ApiResponse> homeController(){
        return ResponseEntity.ok(new ApiResponse("We are in home controller"));
    }
}
