package com.example.pulsar_backend.service;

import com.example.pulsar_backend.dto.LoginRequestDTO;
import com.example.pulsar_backend.dto.SignupRequestDTO;
import com.example.pulsar_backend.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {
    
    UserResponseDTO registerUser(SignupRequestDTO signupRequest);
    
    List<UserResponseDTO> registerUsersInBulk(List<SignupRequestDTO> signupRequests);
    
    UserResponseDTO loginUser(LoginRequestDTO loginRequest);
    
    List<UserResponseDTO> getAllUsers();
    
    UserResponseDTO getUserById(Long id);
    
    UserResponseDTO getUserByUsername(String username);
}
