package com.app_wallet.virtual_wallet.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private boolean read;
}
