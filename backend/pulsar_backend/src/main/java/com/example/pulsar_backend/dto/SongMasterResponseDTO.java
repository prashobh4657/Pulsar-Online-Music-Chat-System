package com.example.pulsar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongMasterResponseDTO {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private Integer durationsec;
    private String duration; // Formatted as "mm:ss"
}
