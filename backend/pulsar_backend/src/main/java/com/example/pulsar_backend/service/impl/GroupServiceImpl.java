package com.example.pulsar_backend.service.impl;

import com.example.pulsar_backend.dao.IGroupDao;
import com.example.pulsar_backend.dao.IUserDao;
import com.example.pulsar_backend.dao.IUserGroupMappingDao;
import com.example.pulsar_backend.entity.GroupMasterEntity;
import com.example.pulsar_backend.entity.UserEntity;
import com.example.pulsar_backend.entity.UserGroupMappingEntity;
import com.example.pulsar_backend.service.IGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements IGroupService {
    private final IGroupDao groupDao;
    private final IUserDao userDao;
    private final IUserGroupMappingDao userGroupMappingDao;

    public GroupMasterEntity createGroup(String name, String description) {
        log.info("Creating group with name: {}", name);
        GroupMasterEntity group = GroupMasterEntity.builder()
                .name(name)
                .description(description)
                .build();
        GroupMasterEntity savedGroup = groupDao.save(group);
        log.info("Group created successfully with ID: {}", savedGroup.getId());
        return savedGroup;
    }

    public List<GroupMasterEntity> createGroupsBulk(List<GroupMasterEntity> groups) {
        log.info("Creating {} groups in bulk", groups.size());
        List<GroupMasterEntity> savedGroups = groupDao.saveAll(groups);
        log.info("Successfully created {} groups", savedGroups.size());
        return savedGroups;
    }

    public String addUserToGroup(Long userId, Long groupId) {
        log.info("Adding user ID: {} to group ID: {}", userId, groupId);
        Optional<UserEntity> userOpt = userDao.findById(userId);
        Optional<GroupMasterEntity> groupOpt = groupDao.findById(groupId);

        if (userOpt.isEmpty() || groupOpt.isEmpty()) {
            log.warn("User or Group not found - userId: {}, groupId: {}", userId, groupId);
            return "User or Group not found";
        }

        // Check if mapping already exists
        Optional<UserGroupMappingEntity> existingMapping = userGroupMappingDao.findByUserIdAndGroupId(userId, groupId);
        if (existingMapping.isPresent()) {
            log.warn("User {} is already in group {}", userId, groupId);
            return "User is already in the group";
        }

        // Create new mapping
        UserGroupMappingEntity mapping = UserGroupMappingEntity.builder()
                .userId(userId)
                .groupId(groupId)
                .build();
        userGroupMappingDao.save(mapping);
        log.info("User {} added to group {} successfully", userId, groupId);

        return "User added to group successfully";
    }

    public Set<UserEntity> getGroupUsers(Long groupId) {
        log.info("Fetching users for group ID: {}", groupId);
        // Verify group exists
        groupDao.findById(groupId)
                .orElseThrow(() -> {
                    log.error("Group not found with ID: {}", groupId);
                    return new RuntimeException("Group not found");
                });
        
        List<UserGroupMappingEntity> mappings = userGroupMappingDao.findByGroupId(groupId);
        Set<UserEntity> users = mappings.stream()
                .map(mapping -> userDao.findById(mapping.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found with ID: " + mapping.getUserId())))
                .collect(Collectors.toSet());
        log.info("Found {} users in group ID: {}", users.size(), groupId);
        return users;
    }

    public Set<GroupMasterEntity> getUserGroups(Long userId) {
        log.info("Fetching groups for user ID: {}", userId);
        // Verify user exists
        userDao.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new RuntimeException("User not found");
                });
        
        List<UserGroupMappingEntity> mappings = userGroupMappingDao.findByUserId(userId);
        Set<GroupMasterEntity> groups = mappings.stream()
                .map(mapping -> groupDao.findById(mapping.getGroupId())
                        .orElseThrow(() -> new RuntimeException("Group not found with ID: " + mapping.getGroupId())))
                .collect(Collectors.toSet());
        log.info("Found {} groups for user ID: {}", groups.size(), userId);
        return groups;
    }


}
