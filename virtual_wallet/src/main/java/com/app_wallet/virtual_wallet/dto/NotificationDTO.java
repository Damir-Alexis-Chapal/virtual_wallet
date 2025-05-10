package com.app_wallet.virtual_wallet.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private Long id;
    private String message;
    private LocalDateTime date;
    private boolean read;

    public NotificationDTO() {}

    public NotificationDTO(Long id, String message, LocalDateTime date, boolean read) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.read = read;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}
