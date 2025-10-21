package com.example.pulsar_backend.repository;

import com.example.pulsar_backend.entity.GroupMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupMasterEntity, Long> {
    
    @Query("SELECT g FROM GroupMasterEntity g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<GroupMasterEntity> searchByName(@Param("search") String search);
}