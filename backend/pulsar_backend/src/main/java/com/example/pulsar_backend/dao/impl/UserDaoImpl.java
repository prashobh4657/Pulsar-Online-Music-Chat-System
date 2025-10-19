package com.example.pulsar_backend.dao.impl;

import com.example.pulsar_backend.dao.IUserDao;
import com.example.pulsar_backend.entity.UserEntity;
import com.example.pulsar_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDaoImpl implements IUserDao {
    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {
        log.debug("DAO: Saving user with username: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> saveAll(List<UserEntity> users) {
        log.debug("DAO: Saving {} users", users.size());
        return userRepository.saveAll(users);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        log.debug("DAO: Finding user by ID: {}", id);
        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> findAllById(List<Long> ids) {
        log.debug("DAO: Finding users by IDs: {}", ids);
        return userRepository.findAllById(ids);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        log.debug("DAO: Finding user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        log.debug("DAO: Finding user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        log.debug("DAO: Checking if user exists by ID: {}", id);
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        log.debug("DAO: Checking if username exists: {}", username);
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.debug("DAO: Checking if email exists: {}", email);
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<UserEntity> findAll() {
        log.debug("DAO: Finding all users");
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        log.debug("DAO: Deleting user by ID: {}", id);
        userRepository.deleteById(id);
    }
}
