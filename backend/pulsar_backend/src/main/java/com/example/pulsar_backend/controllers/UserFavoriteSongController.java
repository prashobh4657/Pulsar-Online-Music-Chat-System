package com.example.pulsar_backend.controllers;

import com.example.pulsar_backend.dto.ApiResponse;
import com.example.pulsar_backend.dto.SongMasterResponseDTO;
import com.example.pulsar_backend.entity.SongMasterEntity;
import com.example.pulsar_backend.service.IUserFavoriteSongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/favorites")
@RequiredArgsConstructor
@Slf4j
public class UserFavoriteSongController {
    private final IUserFavoriteSongService userFavoriteSongService;

    @PostMapping("/user/{userId}/song/{songId}")
    public ResponseEntity<ApiResponse<String>> addFavoriteSong(
            @PathVariable Long userId,
            @PathVariable Long songId) {
        log.info("POST /v1/api/favorites/user/{}/song/{} - Adding song to favorites", userId, songId);
        String result = userFavoriteSongService.addFavoriteSong(userId, songId);
        log.info("POST /v1/api/favorites/user/{}/song/{} - {}", userId, songId, result);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildSuccessResponse(result, null));
    }

    @DeleteMapping("/user/{userId}/song/{songId}")
    public ResponseEntity<ApiResponse<String>> removeFavoriteSong(
            @PathVariable Long userId,
            @PathVariable Long songId) {
        log.info("DELETE /v1/api/favorites/user/{}/song/{} - Removing song from favorites", userId, songId);
        String result = userFavoriteSongService.removeFavoriteSong(userId, songId);
        log.info("DELETE /v1/api/favorites/user/{}/song/{} - {}", userId, songId, result);
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse(result, null));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<SongMasterEntity>>> getUserFavoriteSongs(@PathVariable Long userId) {
        log.info("GET /v1/api/favorites/user/{} - Fetching favorite songs", userId);
        List<SongMasterEntity> songs = userFavoriteSongService.getUserFavoriteSongs(userId);
        log.info("GET /v1/api/favorites/user/{} - Found {} favorite songs", userId, songs.size());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Favorite songs retrieved successfully", songs));
    }

    @GetMapping("/user/{userId}/songs")
    public ResponseEntity<ApiResponse<List<SongMasterResponseDTO>>> getUserFavoriteSongsWithDuration(@PathVariable Long userId) {
        log.info("GET /v1/api/favorites/user/{}/songs - Fetching favorite songs with formatted duration", userId);
        List<SongMasterResponseDTO> songs = userFavoriteSongService.getUserFavoriteSongsWithDuration(userId);
        log.info("GET /v1/api/favorites/user/{}/songs - Found {} favorite songs", userId, songs.size());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Favorite songs retrieved successfully", songs));
    }

    @GetMapping("/user/{userId}/song/{songId}/check")
    public ResponseEntity<ApiResponse<Boolean>> isFavoriteSong(
            @PathVariable Long userId,
            @PathVariable Long songId) {
        log.info("GET /v1/api/favorites/user/{}/song/{}/check - Checking if song is favorite", userId, songId);
        boolean isFavorite = userFavoriteSongService.isFavoriteSong(userId, songId);
        log.info("GET /v1/api/favorites/user/{}/song/{}/check - Result: {}", userId, songId, isFavorite);
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Check completed", isFavorite));
    }
}
