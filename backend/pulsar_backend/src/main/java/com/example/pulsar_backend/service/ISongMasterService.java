package com.example.pulsar_backend.service;

import com.example.pulsar_backend.dto.SongMasterDTO;

import java.util.List;

public interface ISongMasterService {
    SongMasterDTO addSong(SongMasterDTO songDTO);
    List<SongMasterDTO> addSongsInBulk(List<SongMasterDTO> songDTOList);
    SongMasterDTO getSongById(Long id);
}
