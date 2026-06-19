package com.userservice.controller;

import com.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse homeController(){
        return new ApiResponse("User serrvice of airline system");
    }
}
