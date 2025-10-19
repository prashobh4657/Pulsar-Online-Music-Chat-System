package com.example.pulsar_backend.dao;

import com.example.pulsar_backend.entity.FriendEntity;

import java.util.List;
import java.util.Optional;

public interface IFriendDao {
    FriendEntity save(FriendEntity friend);
    Optional<FriendEntity> findById(Long id);
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
    Optional<FriendEntity> findByUserIdAndFriendId(Long userId, Long friendId);
    List<FriendEntity> findFriendsByUserIdAndStatus(Long userId, String status);
    List<FriendEntity> findAllByFriendIdAndStatus(Long friendId, String status);
    void deleteById(Long id);
}
