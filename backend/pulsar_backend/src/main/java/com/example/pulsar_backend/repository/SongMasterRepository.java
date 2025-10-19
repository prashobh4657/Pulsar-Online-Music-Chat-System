package com.example.pulsar_backend.repository;

import com.example.pulsar_backend.entity.SongMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongMasterRepository extends JpaRepository<SongMasterEntity, Long> {
}
