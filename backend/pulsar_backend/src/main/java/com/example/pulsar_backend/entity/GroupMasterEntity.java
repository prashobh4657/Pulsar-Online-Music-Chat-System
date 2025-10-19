package com.example.pulsar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GROUP_MASTER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;
}
