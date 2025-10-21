package com.example.pulsar_backend.repository;

import com.example.pulsar_backend.entity.UserFavoriteSongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavoriteSongRepository extends JpaRepository<UserFavoriteSongEntity, Long> {
    Optional<UserFavoriteSongEntity> findByUserIdAndSongId(Long userId, Long songId);
    List<UserFavoriteSongEntity> findByUserId(Long userId);
    boolean existsByUserIdAndSongId(Long userId, Long songId);
}
