package com.example.pulsar_backend.dto;

import com.example.pulsar_backend.entity.GroupMasterEntity;
import com.example.pulsar_backend.entity.SongMasterEntity;
import com.example.pulsar_backend.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultDTO {
    private List<UserEntity> userInfo;
    private List<GroupMasterEntity> groupInfo;
    private List<SongMasterEntity> songInfo;
}
