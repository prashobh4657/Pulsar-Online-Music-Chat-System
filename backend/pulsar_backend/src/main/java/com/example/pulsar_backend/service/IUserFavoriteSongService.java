package com.example.pulsar_backend.service;

import com.example.pulsar_backend.dto.SongMasterResponseDTO;
import com.example.pulsar_backend.entity.SongMasterEntity;

import java.util.List;

public interface IUserFavoriteSongService {
    String addFavoriteSong(Long userId, Long songId);
    String removeFavoriteSong(Long userId, Long songId);
    List<SongMasterEntity> getUserFavoriteSongs(Long userId);
    List<SongMasterResponseDTO> getUserFavoriteSongsWithDuration(Long userId);
    boolean isFavoriteSong(Long userId, Long songId);
}
