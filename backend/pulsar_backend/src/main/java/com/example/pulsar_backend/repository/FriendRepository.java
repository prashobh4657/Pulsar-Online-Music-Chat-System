package com.example.pulsar_backend.repository;

import com.example.pulsar_backend.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
    Optional<FriendEntity> findByUserIdAndFriendId(Long userId, Long friendId);
    @Query("SELECT f FROM FriendEntity f WHERE f.status = :status AND (f.userId = :userId OR f.friendId = :userId)")
    List<FriendEntity> findFriendsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") String status);
    List<FriendEntity> findAllByFriendIdAndStatus(Long friendId, String status);
}
