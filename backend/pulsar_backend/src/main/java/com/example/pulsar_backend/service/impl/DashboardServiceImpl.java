package com.example.pulsar_backend.service.impl;

import com.example.pulsar_backend.dto.SearchResultDTO;
import com.example.pulsar_backend.entity.GroupMasterEntity;
import com.example.pulsar_backend.entity.SongMasterEntity;
import com.example.pulsar_backend.entity.UserEntity;
import com.example.pulsar_backend.repository.GroupRepository;
import com.example.pulsar_backend.repository.SongMasterRepository;
import com.example.pulsar_backend.repository.UserRepository;
import com.example.pulsar_backend.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements IDashboardService {
    
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SongMasterRepository songMasterRepository;
    
    @Override
    public SearchResultDTO search(String searchTerm) {
        log.info("Searching for: {}", searchTerm);
        
        // Search users by username or fullname
        List<UserEntity> users = userRepository.searchByUsernameOrFullname(searchTerm);
        log.info("Found {} users", users.size());
        
        // Search groups by name
        List<GroupMasterEntity> groups = groupRepository.searchByName(searchTerm);
        log.info("Found {} groups", groups.size());
        
        // Search songs by title
        List<SongMasterEntity> songs = songMasterRepository.searchByTitle(searchTerm);
        log.info("Found {} songs", songs.size());
        
        return SearchResultDTO.builder()
                .userInfo(users)
                .groupInfo(groups)
                .songInfo(songs)
                .build();
    }
}
