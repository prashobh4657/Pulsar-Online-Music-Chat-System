package com.example.pulsar_backend.dao.impl;

import com.example.pulsar_backend.dao.ISongMasterDao;
import com.example.pulsar_backend.entity.SongMasterEntity;
import com.example.pulsar_backend.repository.SongMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SongMasterDaoImpl implements ISongMasterDao {
    private final SongMasterRepository songMasterRepository;

    @Override
    public SongMasterEntity save(SongMasterEntity song) {
        log.debug("DAO: Saving song with title: {}", song.getTitle());
        return songMasterRepository.save(song);
    }

    @Override
    public List<SongMasterEntity> saveAll(List<SongMasterEntity> songs) {
        log.debug("DAO: Saving {} songs", songs.size());
        return songMasterRepository.saveAll(songs);
    }

    @Override
    public Optional<SongMasterEntity> findById(Long id) {
        log.debug("DAO: Finding song by ID: {}", id);
        return songMasterRepository.findById(id);
    }

    @Override
    public List<SongMasterEntity> findAll() {
        log.debug("DAO: Finding all songs");
        return songMasterRepository.findAll();
    }

    @Override
    public List<SongMasterEntity> findAllByIdIn(List<Long> ids) {
        log.debug("DAO: Finding songs by IDs: {}", ids);
        return songMasterRepository.findAllByIdIn(ids);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("DAO: Deleting song by ID: {}", id);
        songMasterRepository.deleteById(id);
    }
}
