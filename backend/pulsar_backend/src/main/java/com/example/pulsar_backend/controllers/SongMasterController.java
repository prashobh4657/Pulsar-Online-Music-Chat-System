package com.example.pulsar_backend.controllers;

import com.example.pulsar_backend.dto.ApiResponse;
import com.example.pulsar_backend.dto.SongMasterDTO;
import com.example.pulsar_backend.service.ISongMasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/songs")
@RequiredArgsConstructor
@Slf4j
public class SongMasterController {

    private final ISongMasterService songMasterService;

    @PostMapping
    public ResponseEntity<ApiResponse<SongMasterDTO>> addSong(@Valid @RequestBody SongMasterDTO songDTO) {
        log.info("POST /v1/api/songs - Request received to add song: {}", songDTO.getTitle());
        SongMasterDTO savedSong = songMasterService.addSong(songDTO);
        log.info("POST /v1/api/songs - Song added successfully with ID: {}", savedSong.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildSuccessResponse("Song added successfully", savedSong));
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<SongMasterDTO>>> addSongsInBulk(@Valid @RequestBody List<SongMasterDTO> songDTOList) {
        log.info("POST /v1/api/songs/bulk - Request received to add {} songs in bulk", songDTOList.size());
        List<SongMasterDTO> savedSongs = songMasterService.addSongsInBulk(songDTOList);
        log.info("POST /v1/api/songs/bulk - Successfully added {} songs", savedSongs.size());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.buildSuccessResponse("Songs added successfully", savedSongs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SongMasterDTO>> getSongById(@PathVariable Long id) {
        log.info("GET /v1/api/songs/{} - Request received to fetch song by ID", id);
        SongMasterDTO song = songMasterService.getSongById(id);
        log.info("GET /v1/api/songs/{} - Successfully retrieved song: {}", id, song.getTitle());
        return ResponseEntity.ok(ApiResponse.buildSuccessResponse("Song retrieved successfully", song));
    }
}
