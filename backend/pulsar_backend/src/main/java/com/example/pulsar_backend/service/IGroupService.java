package com.example.pulsar_backend.service;

import com.example.pulsar_backend.entity.GroupMasterEntity;
import com.example.pulsar_backend.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface IGroupService {
    GroupMasterEntity createGroup(String name, String description);
    List<GroupMasterEntity> createGroupsBulk(List<GroupMasterEntity> groups);
    String addUserToGroup(Long userId, Long groupId);
    Set<UserEntity> getGroupUsers(Long groupId);
    Set<GroupMasterEntity> getUserGroups(Long userId);
}
