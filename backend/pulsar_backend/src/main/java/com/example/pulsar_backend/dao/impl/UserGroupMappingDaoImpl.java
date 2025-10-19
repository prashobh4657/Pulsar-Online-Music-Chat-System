package com.example.pulsar_backend.dao.impl;

import com.example.pulsar_backend.dao.IUserGroupMappingDao;
import com.example.pulsar_backend.entity.UserGroupMappingEntity;
import com.example.pulsar_backend.repository.UserGroupMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserGroupMappingDaoImpl implements IUserGroupMappingDao {
    private final UserGroupMappingRepository userGroupMappingRepository;

    @Override
    public UserGroupMappingEntity save(UserGroupMappingEntity mapping) {
        log.debug("DAO: Saving user-group mapping - userId: {}, groupId: {}", 
                mapping.getUserId(), mapping.getGroupId());
        return userGroupMappingRepository.save(mapping);
    }

    @Override
    public Optional<UserGroupMappingEntity> findByUserIdAndGroupId(Long userId, Long groupId) {
        log.debug("DAO: Finding mapping by userId: {} and groupId: {}", userId, groupId);
        return userGroupMappingRepository.findByUserIdAndGroupId(userId, groupId);
    }

    @Override
    public List<UserGroupMappingEntity> findByGroupId(Long groupId) {
        log.debug("DAO: Finding mappings by groupId: {}", groupId);
        return userGroupMappingRepository.findByGroupId(groupId);
    }

    @Override
    public List<UserGroupMappingEntity> findByUserId(Long userId) {
        log.debug("DAO: Finding mappings by userId: {}", userId);
        return userGroupMappingRepository.findByUserId(userId);
    }

    @Override
    public void deleteByUserIdAndGroupId(Long userId, Long groupId) {
        log.debug("DAO: Deleting mapping - userId: {}, groupId: {}", userId, groupId);
        userGroupMappingRepository.deleteByUserIdAndGroupId(userId, groupId);
    }
}
