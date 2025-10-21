package com.example.pulsar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime time;
    private Integer unread;
}
