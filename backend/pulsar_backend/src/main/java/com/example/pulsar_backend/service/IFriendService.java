package com.example.pulsar_backend.service;

import com.example.pulsar_backend.dto.FriendListResponseDTO;
import com.example.pulsar_backend.entity.FriendEntity;

import java.util.List;

public interface IFriendService {
    FriendEntity sendRequest(Long userId, Long friendId);
    FriendEntity acceptRequest(Long userId, Long friendId);
    void rejectRequest(Long userId, Long friendId);
    List<FriendEntity> listFriends(Long userId);
    List<FriendEntity> listPendingRequests(Long userId);
    List<FriendListResponseDTO> getFriendsList(Long userId);
}
