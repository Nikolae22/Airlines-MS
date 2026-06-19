package com.userservice.service.impl;

import com.enums.UserRole;
import com.payload.dto.UserDTO;
import com.payload.response.AuthResponse;
import com.userservice.config.JwtProvider;
import com.userservice.mapper.UserMapper;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import com.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;


    /**
     * 1 check if email already exists
     * 2 Encode password using ByCript
     * 3 save user in database
     * 4 Generate JWT token
     * 5 Return token adn user information
     * @param userDTO
     */
    @Override
    public AuthResponse signup(UserDTO userDTO) throws Exception {
        User userExist = userRepository.findByEmail(userDTO.getEmail());
        if (userExist !=null){
            throw new Exception("email already exists");
        }
        if (userDTO.getRole() == UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("You cannot sign up system admin");
        }
        User newUser=User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .phone(userDTO.getPhone())
                .role(userDTO.getRole())
                .fullName(userDTO.getFullName())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        User saved = userRepository.save(newUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(saved.getEmail(),saved.getPassword());
        String jwt=jwtProvider.generateToken(authentication,saved.getId());
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(saved));
        authResponse.setTitle("Welcome "+saved.getFullName());
        authResponse.setMessage("Registered Successfully!");
        return authResponse;
    }

    /**
     * 1 Load user by email
     * 2 Compare password with BCrypt
     * 3 Update lastLogin time
     * 4 Generate JWT token
     * 5 return token and user information
     * @param email
     * @param password
     */
    @Override
    public AuthResponse login(String email, String password) throws Exception {
        Authentication authentication=authentication(email,password);

        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        String jwt = jwtProvider.generateToken(authentication, user.getId());
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(user));
        authResponse.setTitle("Welcome back "+user.getFullName());
        authResponse.setMessage("Login Successfully");
        return authResponse;
    }

    private Authentication authentication(String email,String password) throws Exception {
        UserDetails userDetails=customUserDetailsService.loadUserByUsername(email);
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new Exception("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
