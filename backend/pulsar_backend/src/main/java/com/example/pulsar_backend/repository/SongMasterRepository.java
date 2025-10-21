package com.example.pulsar_backend.repository;

import com.example.pulsar_backend.entity.SongMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongMasterRepository extends JpaRepository<SongMasterEntity, Long> {
    List<SongMasterEntity> findAllByIdIn(List<Long> ids);
    
    @Query("SELECT s FROM SongMasterEntity s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<SongMasterEntity> searchByTitle(@Param("search") String search);
}
