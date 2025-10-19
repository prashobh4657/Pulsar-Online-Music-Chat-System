package com.example.pulsar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USER_GROUP_MAPPING")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "GROUP_ID", nullable = false)
    private Long groupId;
}
