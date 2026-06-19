package com.userservice.service;

import com.payload.dto.UserDTO;
import com.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password) throws Exception;
    AuthResponse signup(UserDTO userDTO) throws Exception;

}
