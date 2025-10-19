package com.example.pulsar_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SONG_MASTER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "ARTIST", nullable = false)
    private String artist;

    @Column(name = "ALBUM")
    private String album;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "DURATION_SEC", nullable = false)
    private Integer durationsec;
}
