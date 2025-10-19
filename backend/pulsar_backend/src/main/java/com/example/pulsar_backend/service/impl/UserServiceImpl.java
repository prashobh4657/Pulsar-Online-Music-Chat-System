package com.example.pulsar_backend.service.impl;

import com.example.pulsar_backend.dto.LoginRequestDTO;
import com.example.pulsar_backend.dto.SignupRequestDTO;
import com.example.pulsar_backend.dto.UserResponseDTO;
import com.example.pulsar_backend.entity.UserEntity;
import com.example.pulsar_backend.exception.InvalidCredentialsException;
import com.example.pulsar_backend.exception.UserAlreadyExistsException;
import com.example.pulsar_backend.exception.UserNotFoundException;
import com.example.pulsar_backend.repository.UserRepository;
import com.example.pulsar_backend.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDTO registerUser(SignupRequestDTO signupRequest) {
        log.info("Attempting to register user with username: {}", signupRequest.getUserName());
        
        validateUserCanBeAdded(signupRequest);
        
        // Create new user entity
        UserEntity userEntity = UserEntity.builder()
                .fullname(signupRequest.getFullName())
                .username(signupRequest.getUserName())
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword()) // TODO: Hash password with BCrypt
                .build();
        
        // Save user
        UserEntity savedUser = userRepository.save(userEntity);
        log.info("User registered successfully with ID: {}", savedUser.getId());
        
        return mapToUserResponseDTO(savedUser);
    }

    @Override
    @Transactional
    public List<UserResponseDTO> registerUsersInBulk(List<SignupRequestDTO> signupRequests) {
        log.info("Attempting to register {} users in bulk", signupRequests.size());
        
        List<UserEntity> userEntities = signupRequests.stream()
                .map(signupRequest -> {
                    validateUserCanBeAdded(signupRequest);
                    
                    // Create new user entity
                    return UserEntity.builder()
                            .fullname(signupRequest.getFullName())
                            .username(signupRequest.getUserName())
                            .email(signupRequest.getEmail())
                            .password(signupRequest.getPassword()) // TODO: Hash password with BCrypt
                            .build();
                })
                .collect(Collectors.toList());
        
        // Save all users
        List<UserEntity> savedUsers = userRepository.saveAll(userEntities);
        log.info("Successfully registered {} users in bulk", savedUsers.size());
        
        return savedUsers.stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO loginUser(LoginRequestDTO loginRequest) {
        log.info("Attempting login for username: {}", loginRequest.getUsername());
        
        // Find user by username
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    log.warn("User not found: {}", loginRequest.getUsername());
                    return new UserNotFoundException("Username not found");
                });
        
        // Verify password (TODO: Use BCrypt password encoder)
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            log.warn("Invalid password for user: {}", loginRequest.getUsername());
            throw new InvalidCredentialsException("Invalid password");
        }
        
        log.info("User logged in successfully: {}", loginRequest.getUsername());
        return mapToUserResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return mapToUserResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByUsername(String username) {
        log.info("Fetching user by username: {}", username);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return mapToUserResponseDTO(user);
    }

    private void validateUserCanBeAdded(SignupRequestDTO signupRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(signupRequest.getUserName())) {
            log.warn("Username already exists: {}", signupRequest.getUserName());
            throw new UserAlreadyExistsException("Username already exists: " + signupRequest.getUserName());
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            log.warn("Email already exists: {}", signupRequest.getEmail());
            throw new UserAlreadyExistsException("Email already exists: " + signupRequest.getEmail());
        }
    }

    private UserResponseDTO mapToUserResponseDTO(UserEntity user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
