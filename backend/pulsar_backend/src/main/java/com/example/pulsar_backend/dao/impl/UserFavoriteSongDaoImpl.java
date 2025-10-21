package com.example.pulsar_backend.dao.impl;

import com.example.pulsar_backend.dao.IUserFavoriteSongDao;
import com.example.pulsar_backend.entity.UserFavoriteSongEntity;
import com.example.pulsar_backend.repository.UserFavoriteSongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserFavoriteSongDaoImpl implements IUserFavoriteSongDao {
    private final UserFavoriteSongRepository userFavoriteSongRepository;

    @Override
    public UserFavoriteSongEntity save(UserFavoriteSongEntity favoriteSong) {
        log.debug("DAO: Saving favorite song for user: {} and song: {}", favoriteSong.getUserId(), favoriteSong.getSongId());
        return userFavoriteSongRepository.save(favoriteSong);
    }

    @Override
    public Optional<UserFavoriteSongEntity> findByUserIdAndSongId(Long userId, Long songId) {
        log.debug("DAO: Finding favorite song for user: {} and song: {}", userId, songId);
        return userFavoriteSongRepository.findByUserIdAndSongId(userId, songId);
    }

    @Override
    public List<UserFavoriteSongEntity> findByUserId(Long userId) {
        log.debug("DAO: Finding all favorite songs for user: {}", userId);
        return userFavoriteSongRepository.findByUserId(userId);
    }

    @Override
    public void delete(UserFavoriteSongEntity favoriteSong) {
        log.debug("DAO: Deleting favorite song with ID: {}", favoriteSong.getId());
        userFavoriteSongRepository.delete(favoriteSong);
    }

    @Override
    public boolean existsByUserIdAndSongId(Long userId, Long songId) {
        log.debug("DAO: Checking if favorite song exists for user: {} and song: {}", userId, songId);
        return userFavoriteSongRepository.existsByUserIdAndSongId(userId, songId);
    }
}
