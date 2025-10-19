package com.example.pulsar_backend.dao.impl;

import com.example.pulsar_backend.dao.IFriendDao;
import com.example.pulsar_backend.entity.FriendEntity;
import com.example.pulsar_backend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class FriendDaoImpl implements IFriendDao {
    private final FriendRepository friendRepository;

    @Override
    public FriendEntity save(FriendEntity friend) {
        log.debug("DAO: Saving friend entity - userId: {}, friendId: {}", 
                friend.getUserId(), friend.getFriendId());
        return friendRepository.save(friend);
    }

    @Override
    public Optional<FriendEntity> findById(Long id) {
        log.debug("DAO: Finding friend by ID: {}", id);
        return friendRepository.findById(id);
    }

    @Override
    public boolean existsByUserIdAndFriendId(Long userId, Long friendId) {
        log.debug("DAO: Checking if friendship exists - userId: {}, friendId: {}", userId, friendId);
        return friendRepository.existsByUserIdAndFriendId(userId, friendId);
    }

    @Override
    public Optional<FriendEntity> findByUserIdAndFriendId(Long userId, Long friendId) {
        log.debug("DAO: Finding friendship - userId: {}, friendId: {}", userId, friendId);
        return friendRepository.findByUserIdAndFriendId(userId, friendId);
    }

    @Override
    public List<FriendEntity> findFriendsByUserIdAndStatus(Long userId, String status) {
        log.debug("DAO: Finding friends for userId: {} with status: {}", userId, status);
        return friendRepository.findFriendsByUserIdAndStatus(userId, status);
    }

    @Override
    public List<FriendEntity> findAllByFriendIdAndStatus(Long friendId, String status) {
        log.debug("DAO: Finding pending requests for friendId: {} with status: {}", friendId, status);
        return friendRepository.findAllByFriendIdAndStatus(friendId, status);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("DAO: Deleting friend by ID: {}", id);
        friendRepository.deleteById(id);
    }
}
