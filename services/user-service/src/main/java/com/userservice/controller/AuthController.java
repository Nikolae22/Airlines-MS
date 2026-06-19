package com.userservice.controller;

import com.payload.dto.UserDTO;
import com.payload.request.LoginRequest;
import com.payload.response.AuthResponse;
import com.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody @Valid UserDTO user) throws Exception {
        AuthResponse signuped = authService.signup(user);
        return ResponseEntity.ok(signuped);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest req) throws Exception {
        AuthResponse login = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(login);
    }
}
