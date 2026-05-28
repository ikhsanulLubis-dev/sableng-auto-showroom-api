package com.sablengauto.showroomapi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AppLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private String endpoint;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime createdAt;
}
