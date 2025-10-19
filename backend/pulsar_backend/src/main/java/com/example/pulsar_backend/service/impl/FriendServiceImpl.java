package com.example.pulsar_backend.service.impl;

import com.example.pulsar_backend.dto.FriendListResponseDTO;
import com.example.pulsar_backend.dto.FriendshipDTO;
import com.example.pulsar_backend.entity.FriendEntity;
import com.example.pulsar_backend.enums.FriendshipStatus;
import com.example.pulsar_backend.exception.ResourceNotFoundException;
import com.example.pulsar_backend.exception.UserAlreadyExistsException;
import com.example.pulsar_backend.repository.FriendRepository;
import com.example.pulsar_backend.repository.UserRepository;
import com.example.pulsar_backend.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public FriendEntity sendRequest(Long userId, Long friendId) {
        log.info("User {} sending friend request to user {}", userId, friendId);
        
        // Validate users exist
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        if (!userRepository.existsById(friendId)) {
            throw new ResourceNotFoundException("Friend not found with ID: " + friendId);
        }
        
        // Ensure userId < friendId constraint
        FriendshipDTO friendship = normalizeFriendship(userId, friendId);
        
        // Check if request already exists
        if (friendRepository.existsByUserIdAndFriendId(friendship.getSmallerId(), friendship.getLargerId())) {
            throw new UserAlreadyExistsException("Friend request already exists");
        }
        
        // Create friend request with userId < friendId
        FriendEntity friendEntity = FriendEntity.builder()
                .userId(friendship.getSmallerId())
                .friendId(friendship.getLargerId())
                .status(FriendshipStatus.PENDING.name())
                .createdAt(LocalDateTime.now())
                .build();
        
        FriendEntity savedRequest = friendRepository.save(friendEntity);
        log.info("Friend request sent successfully with ID: {} (userId={}, friendId={})", 
                savedRequest.getId(), friendship.getSmallerId(), friendship.getLargerId());
        return savedRequest;
    }

    @Override
    @Transactional
    public FriendEntity acceptRequest(Long userId, Long friendId) {
        log.info("User {} accepting friend request from user {}", userId, friendId);
        
        // Ensure userId < friendId constraint
        FriendshipDTO friendship = normalizeFriendship(userId, friendId);
        
        // Find the friend request
        FriendEntity friendRequest = friendRepository.findByUserIdAndFriendId(friendship.getSmallerId(), friendship.getLargerId())
                .orElseThrow(() -> new ResourceNotFoundException("Friend request not found"));
        
        if (!FriendshipStatus.PENDING.name().equals(friendRequest.getStatus())) {
            throw new IllegalStateException("Friend request is not in PENDING status");
        }
        
        // Update status to ACCEPTED
        friendRequest.setStatus(FriendshipStatus.ACCEPTED.name());
        FriendEntity updatedRequest = friendRepository.save(friendRequest);
        log.info("Friend request accepted successfully");
        return updatedRequest;
    }

    @Override
    @Transactional
    public void rejectRequest(Long userId, Long friendId) {
        log.info("User {} rejecting friend request from user {}", userId, friendId);
        
        // Ensure userId < friendId constraint
        FriendshipDTO friendship = normalizeFriendship(userId, friendId);
        
        // Find the friend request
        FriendEntity friendRequest = friendRepository.findByUserIdAndFriendId(friendship.getSmallerId(), friendship.getLargerId())
                .orElseThrow(() -> new ResourceNotFoundException("Friend request not found"));
        
        if (!FriendshipStatus.PENDING.name().equals(friendRequest.getStatus())) {
            throw new IllegalStateException("Friend request is not in PENDING status");
        }
        
        // Update status to REJECTED or delete
        friendRequest.setStatus(FriendshipStatus.REJECTED.name());
        friendRepository.save(friendRequest);
        log.info("Friend request rejected successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendEntity> listFriends(Long userId) {
        log.info("Fetching friends list for user {}", userId);
        
        // Get all accepted friend relationships where user is either userId or friendId
        List<FriendEntity> sentRequests = friendRepository.findFriendsByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED.name());
        
        log.info("Found {} friends for user {}", sentRequests.size(), userId);
        return sentRequests;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendEntity> listPendingRequests(Long userId) {
        log.info("Fetching pending friend requests for user {}", userId);
        
        // Get all pending requests where userId is the recipient (friendId)
        List<FriendEntity> pendingRequests = friendRepository.findAllByFriendIdAndStatus(userId, FriendshipStatus.PENDING.name());
        
        log.info("Found {} pending requests for user {}", pendingRequests.size(), userId);
        return pendingRequests;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendListResponseDTO> getFriendsList(Long userId) {
        log.info("Fetching friends list with details for user {}", userId);
        
        // Get all accepted friend relationships
        List<FriendEntity> friends = friendRepository.findFriendsByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED.name());
        
        // Extract all friend IDs
        List<Long> friendIds = friends.stream()
                .map(friend -> friend.getUserId().equals(userId) ? friend.getFriendId() : friend.getUserId())
                .collect(Collectors.toList());
        
        // Fetch all friend users in a single query
        var friendUsers = userRepository.findAllById(friendIds).stream()
                .collect(Collectors.toMap(
                        user -> user.getId(),
                        user -> user.getFullname()
                ));
        
        // Map to response DTO
        List<FriendListResponseDTO> friendsList = friends.stream()
                .map(friend -> {
                    // Determine the actual friend ID (the one that's not the current user)
                    Long actualFriendId = friend.getUserId().equals(userId) ? friend.getFriendId() : friend.getUserId();
                    
                    return FriendListResponseDTO.builder()
                            .friendId(actualFriendId)
                            .friendFullName(friendUsers.getOrDefault(actualFriendId, "Unknown"))
                            .time(friend.getCreatedAt())
                            .unread(5) // Constant value as requested
                            .build();
                })
                .collect(Collectors.toList());
        
        log.info("Found {} friends with details for user {}", friendsList.size(), userId);
        return friendsList;
    }

    private FriendshipDTO normalizeFriendship(Long userId, Long friendId) {
        Long smallerId = Math.min(userId, friendId);
        Long largerId = Math.max(userId, friendId);
        return new FriendshipDTO(smallerId, largerId);
    }
}
