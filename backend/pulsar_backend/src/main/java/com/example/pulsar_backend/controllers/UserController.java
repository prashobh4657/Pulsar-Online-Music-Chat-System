package com.example.pulsar_backend.controllers;

import com.example.pulsar_backend.dto.LoginRequestDTO;
import com.example.pulsar_backend.dto.SignupRequestDTO;
import com.example.pulsar_backend.dto.UserResponseDTO;
import com.example.pulsar_backend.dto.ApiResponse;
import com.example.pulsar_backend.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    
    private final IUserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        log.info("GET /v1/api/users - Request received to fetch all users");
        List<UserResponseDTO> users = userService.getAllUsers();
        log.info("GET /v1/api/users - Successfully retrieved {} users", users.size());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Users retrieved successfully", users));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Long id) {
        log.info("GET /v1/api/users/{} - Request received to fetch user by ID", id);
        UserResponseDTO user = userService.getUserById(id);
        log.info("GET /v1/api/users/{} - Successfully retrieved user: {}", id, user.getUsername());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("User retrieved successfully", user));
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByUsername(@PathVariable String username) {
        log.info("GET /v1/api/users/username/{} - Request received to fetch user by username", username);
        UserResponseDTO user = userService.getUserByUsername(username);
        log.info("GET /v1/api/users/username/{} - Successfully retrieved user with ID: {}", username, user.getId());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("User retrieved successfully", user));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDTO>> signup(@Valid @RequestBody SignupRequestDTO signupRequest) {
        log.info("POST /v1/api/signup - Signup request received for username: {}, email: {}", 
                signupRequest.getUserName(), signupRequest.getEmail());
        UserResponseDTO newUser = userService.registerUser(signupRequest);
        log.info("POST /v1/api/signup - User registered successfully with ID: {}, username: {}", 
                newUser.getId(), newUser.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildSuccessResponse("User registered successfully", newUser));
    }

    @PostMapping("/signup/bulk")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> signupBulk(@Valid @RequestBody List<SignupRequestDTO> signupRequests) {
        log.info("POST /v1/api/signup/bulk - Bulk signup request received for {} users", signupRequests.size());
        List<UserResponseDTO> newUsers = userService.registerUsersInBulk(signupRequests);
        log.info("POST /v1/api/signup/bulk - Successfully registered {} users", newUsers.size());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.buildSuccessResponse("Users registered successfully", newUsers));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("POST /v1/api/login - Login request received for username: {}", loginRequest.getUsername());
        UserResponseDTO user = userService.loginUser(loginRequest);
        log.info("POST /v1/api/login - User logged in successfully: {} (ID: {})", user.getUsername(), user.getId());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Login successful", user));
    }
}
