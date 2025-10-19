package com.example.pulsar_backend.repository;

import com.example.pulsar_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByUsername(String userName);
    
    Optional<UserEntity> findByEmail(String email);
    
    boolean existsByUsername(String userName);
    
    boolean existsByEmail(String email);
}
