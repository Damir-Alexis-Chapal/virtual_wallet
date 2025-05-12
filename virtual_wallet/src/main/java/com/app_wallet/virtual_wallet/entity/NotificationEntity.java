package com.app_wallet.virtual_wallet.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime date;

    private boolean read;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public NotificationEntity() {
    }

    public NotificationEntity(Long id, String message, LocalDateTime date, boolean read, UserEntity user) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.read = read;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
