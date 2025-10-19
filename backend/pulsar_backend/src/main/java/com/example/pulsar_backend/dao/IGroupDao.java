package com.example.pulsar_backend.dao;

import com.example.pulsar_backend.entity.GroupMasterEntity;

import java.util.List;
import java.util.Optional;

public interface IGroupDao {
    GroupMasterEntity save(GroupMasterEntity group);
    List<GroupMasterEntity> saveAll(List<GroupMasterEntity> groups);
    Optional<GroupMasterEntity> findById(Long id);
    List<GroupMasterEntity> findAll();
    void deleteById(Long id);
}
