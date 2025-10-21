package com.example.pulsar_backend.service.impl;

import com.example.pulsar_backend.dao.ISongMasterDao;
import com.example.pulsar_backend.dao.IUserDao;
import com.example.pulsar_backend.dao.IUserFavoriteSongDao;
import com.example.pulsar_backend.dto.SongMasterResponseDTO;
import com.example.pulsar_backend.entity.SongMasterEntity;
import com.example.pulsar_backend.entity.UserFavoriteSongEntity;
import com.example.pulsar_backend.service.IUserFavoriteSongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFavoriteSongServiceImpl implements IUserFavoriteSongService {
    private final IUserFavoriteSongDao userFavoriteSongDao;
    private final IUserDao userDao;
    private final ISongMasterDao songMasterDao;

    @Override
    public String addFavoriteSong(Long userId, Long songId) {
        log.info("Adding favorite song - userId: {}, songId: {}", userId, songId);
        
        // Verify user exists
        userDao.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new RuntimeException("User not found");
                });
        
        // Verify song exists
        songMasterDao.findById(songId)
                .orElseThrow(() -> {
                    log.error("Song not found with ID: {}", songId);
                    return new RuntimeException("Song not found");
                });
        
        // Check if already favorite
        if (userFavoriteSongDao.existsByUserIdAndSongId(userId, songId)) {
            log.warn("Song {} is already in favorites for user {}", songId, userId);
            return "Song is already in favorites";
        }
        
        // Add to favorites
        UserFavoriteSongEntity favoriteSong = UserFavoriteSongEntity.builder()
                .userId(userId)
                .songId(songId)
                .build();
        userFavoriteSongDao.save(favoriteSong);
        log.info("Song {} added to favorites for user {}", songId, userId);
        
        return "Song added to favorites successfully";
    }

    @Override
    public String removeFavoriteSong(Long userId, Long songId) {
        log.info("Removing favorite song - userId: {}, songId: {}", userId, songId);
        
        Optional<UserFavoriteSongEntity> favoriteSong = userFavoriteSongDao.findByUserIdAndSongId(userId, songId);
        
        if (favoriteSong.isEmpty()) {
            log.warn("Favorite song not found for user {} and song {}", userId, songId);
            return "Song is not in favorites";
        }
        
        userFavoriteSongDao.delete(favoriteSong.get());
        log.info("Song {} removed from favorites for user {}", songId, userId);
        
        return "Song removed from favorites successfully";
    }

    @Override
    public List<SongMasterEntity> getUserFavoriteSongs(Long userId) {
        log.info("Fetching favorite songs for user: {}", userId);
        
        // Verify user exists
        userDao.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new RuntimeException("User not found");
                });
        
        List<UserFavoriteSongEntity> favoriteSongs = userFavoriteSongDao.findByUserId(userId);
        List<SongMasterEntity> songs = favoriteSongs.stream()
                .map(fav -> songMasterDao.findById(fav.getSongId())
                        .orElseThrow(() -> new RuntimeException("Song not found with ID: " + fav.getSongId())))
                .collect(Collectors.toList());
        
        log.info("Found {} favorite songs for user {}", songs.size(), userId);
        return songs;
    }

    @Override
    public List<SongMasterResponseDTO> getUserFavoriteSongsWithDuration(Long userId) {
        log.info("Fetching favorite songs with formatted duration for user: {}", userId);
        
        // Verify user exists
        userDao.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new RuntimeException("User not found");
                });
        
        // Get all favorite song mappings
        List<UserFavoriteSongEntity> favoriteSongs = userFavoriteSongDao.findByUserId(userId);
        
        if (favoriteSongs.isEmpty()) {
            log.info("No favorite songs found for user {}", userId);
            return List.of();
        }
        
        // Extract song IDs
        List<Long> songIds = favoriteSongs.stream()
                .map(UserFavoriteSongEntity::getSongId)
                .collect(Collectors.toList());
        
        // Fetch all songs in a single DB call
        List<SongMasterEntity> songs = songMasterDao.findAllByIdIn(songIds);
        
        // Convert to response DTOs
        List<SongMasterResponseDTO> responseDTOs = songs.stream()
                .map(song -> SongMasterResponseDTO.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .artist(song.getArtist())
                        .album(song.getAlbum())
                        .durationsec(song.getDurationsec())
                        .duration(formatDuration(song.getDurationsec()))
                        .build())
                .collect(Collectors.toList());
        
        log.info("Found {} favorite songs for user {}", responseDTOs.size(), userId);
        return responseDTOs;
    }

    @Override
    public boolean isFavoriteSong(Long userId, Long songId) {
        log.debug("Checking if song {} is favorite for user {}", songId, userId);
        return userFavoriteSongDao.existsByUserIdAndSongId(userId, songId);
    }

    private String formatDuration(Integer durationInSeconds) {
        if (durationInSeconds == null || durationInSeconds < 0) {
            return "0:00";
        }
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
