package com.example.pulsar_backend.service;

import com.example.pulsar_backend.dto.SongMasterDTO;
import com.example.pulsar_backend.entity.SongMasterEntity;
import com.example.pulsar_backend.exception.ResourceNotFoundException;
import com.example.pulsar_backend.repository.SongMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongMasterServiceImpl implements ISongMasterService {

    private final SongMasterRepository songMasterRepository;

    @Override
    @Transactional
    public SongMasterDTO addSong(SongMasterDTO songDTO) {
        log.info("Adding new song: {}", songDTO.getTitle());
        SongMasterEntity songEntity = mapToEntity(songDTO);
        SongMasterEntity savedSong = songMasterRepository.save(songEntity);
        log.info("Song added successfully with ID: {}", savedSong.getId());
        return mapToDTO(savedSong);
    }

    @Override
    @Transactional
    public List<SongMasterDTO> addSongsInBulk(List<SongMasterDTO> songDTOList) {
        log.info("Adding {} songs in bulk", songDTOList.size());
        List<SongMasterEntity> songEntities = songDTOList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        
        List<SongMasterEntity> savedSongs = songMasterRepository.saveAll(songEntities);
        log.info("Successfully added {} songs in bulk", savedSongs.size());
        
        return savedSongs.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SongMasterDTO getSongById(Long id) {
        log.info("Fetching song with ID: {}", id);
        SongMasterEntity songEntity = songMasterRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Song not found with ID: {}", id);
                    return new ResourceNotFoundException("Song not found with ID: " + id);
                });
        log.info("Song retrieved successfully: {}", songEntity.getTitle());
        return mapToDTO(songEntity);
    }

    private SongMasterEntity mapToEntity(SongMasterDTO dto) {
        return SongMasterEntity.builder()
                .title(dto.getTitle())
                .artist(dto.getArtist())
                .album(dto.getAlbum())
                .year(dto.getYear())
                .durationsec(dto.getDurationInSeconds())
                .build();
    }

    private SongMasterDTO mapToDTO(SongMasterEntity entity) {
        return SongMasterDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .artist(entity.getArtist())
                .album(entity.getAlbum())
                .year(entity.getYear())
                .durationInSeconds(entity.getDurationsec())
                .build();
    }
}
