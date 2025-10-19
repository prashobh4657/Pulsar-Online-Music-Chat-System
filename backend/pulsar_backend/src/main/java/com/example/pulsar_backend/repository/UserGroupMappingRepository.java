package com.example.pulsar_backend.repository;

import com.example.pulsar_backend.entity.UserGroupMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, Long> {
    
    Optional<UserGroupMappingEntity> findByUserIdAndGroupId(Long userId, Long groupId);
    
    List<UserGroupMappingEntity> findByGroupId(Long groupId);
    
    List<UserGroupMappingEntity> findByUserId(Long userId);
    
    void deleteByUserIdAndGroupId(Long userId, Long groupId);
}
