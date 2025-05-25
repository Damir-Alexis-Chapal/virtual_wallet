package com.app_wallet.virtual_wallet.entity;

import jakarta.persistence.*;

@Entity
@Table(name="benefit_entity")
public class BenefitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean isActive;

    @Column(name = "user_id")
    private Long userId;

    public BenefitEntity() {}

    public BenefitEntity(String title, boolean isActive, Long userId) {
        this.title = title;
        this.isActive = isActive;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
