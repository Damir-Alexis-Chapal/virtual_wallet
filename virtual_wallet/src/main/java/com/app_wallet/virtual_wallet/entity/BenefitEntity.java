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

    public BenefitEntity() {
    }

    public BenefitEntity(Long id, String title, boolean isActive) {
        this.id = id;
        this.title = title;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
