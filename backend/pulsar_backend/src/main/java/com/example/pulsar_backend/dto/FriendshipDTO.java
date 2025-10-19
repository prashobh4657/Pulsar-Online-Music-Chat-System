package com.example.pulsar_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDTO {
    private Long smallerId;
    private Long largerId;
}
