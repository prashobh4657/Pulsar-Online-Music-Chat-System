package com.example.pulsar_backend.dao;

import com.example.pulsar_backend.entity.UserFavoriteSongEntity;

import java.util.List;
import java.util.Optional;

public interface IUserFavoriteSongDao {
    UserFavoriteSongEntity save(UserFavoriteSongEntity favoriteSong);
    Optional<UserFavoriteSongEntity> findByUserIdAndSongId(Long userId, Long songId);
    List<UserFavoriteSongEntity> findByUserId(Long userId);
    void delete(UserFavoriteSongEntity favoriteSong);
    boolean existsByUserIdAndSongId(Long userId, Long songId);
}
