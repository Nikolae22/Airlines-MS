package com.userservice.service;

import com.payload.dto.UserDTO;
import com.userservice.model.User;

import java.util.List;

public interface UserService {

    UserDTO getUserByEmail(String email) throws Exception;
    UserDTO getUserById(Long id) throws Exception;
    List<UserDTO> getAllUsers();
}
