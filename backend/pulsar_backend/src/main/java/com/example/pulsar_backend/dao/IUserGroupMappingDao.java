package com.example.pulsar_backend.dao;

import com.example.pulsar_backend.entity.UserGroupMappingEntity;

import java.util.List;
import java.util.Optional;

public interface IUserGroupMappingDao {
    UserGroupMappingEntity save(UserGroupMappingEntity mapping);
    Optional<UserGroupMappingEntity> findByUserIdAndGroupId(Long userId, Long groupId);
    List<UserGroupMappingEntity> findByGroupId(Long groupId);
    List<UserGroupMappingEntity> findByUserId(Long userId);
    void deleteByUserIdAndGroupId(Long userId, Long groupId);
}
