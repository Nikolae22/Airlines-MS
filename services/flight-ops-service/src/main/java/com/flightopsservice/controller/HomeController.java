package com.flightopsservice.controller;

import com.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        return new ApiResponse("In flight ms");
    }
}
