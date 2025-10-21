package com.example.pulsar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USER_FAVORITE_SONG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavoriteSongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "SONG_ID", nullable = false)
    private Long songId;
}
