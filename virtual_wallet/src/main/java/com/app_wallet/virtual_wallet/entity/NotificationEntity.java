package com.app_wallet.virtual_wallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private String message;
    
    @Column(nullable = false)
    private String type; // INFO, WARNING, ALERT
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private boolean read = false;
}
