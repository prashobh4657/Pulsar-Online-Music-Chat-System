package com.example.pulsar_backend.dao;

import com.example.pulsar_backend.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserDao {
    UserEntity save(UserEntity user);
    List<UserEntity> saveAll(List<UserEntity> users);
    Optional<UserEntity> findById(Long id);
    List<UserEntity> findAllById(List<Long> ids);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<UserEntity> findAll();
    void deleteById(Long id);
}
