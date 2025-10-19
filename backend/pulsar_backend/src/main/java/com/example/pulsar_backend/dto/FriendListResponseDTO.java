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
public class FriendListResponseDTO {
    private Long friendId;
    private String friendFullName;
    private LocalDateTime time;
    private Integer unread;
}
