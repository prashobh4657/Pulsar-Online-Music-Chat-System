package com.example.pulsar_backend.repository;

import com.example.pulsar_backend.entity.GroupMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupMasterEntity, Long> {
}