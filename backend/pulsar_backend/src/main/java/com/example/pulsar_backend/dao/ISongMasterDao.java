package com.example.pulsar_backend.dao;

import com.example.pulsar_backend.entity.SongMasterEntity;

import java.util.List;
import java.util.Optional;

public interface ISongMasterDao {
    SongMasterEntity save(SongMasterEntity song);
    List<SongMasterEntity> saveAll(List<SongMasterEntity> songs);
    Optional<SongMasterEntity> findById(Long id);
    List<SongMasterEntity> findAll();
    void deleteById(Long id);
}
