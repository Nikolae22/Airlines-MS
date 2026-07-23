package com.paymentservice.controller;

import com.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {


    @GetMapping
    public ApiResponse hello(){
        return new ApiResponse("Hello from payment service");
    }
}
