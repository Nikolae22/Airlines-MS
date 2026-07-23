package com.bookingservice.controller;

import com.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("Hello everyone in booking-service ms");
        return apiResponse;
    }
}
