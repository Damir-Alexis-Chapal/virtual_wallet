package com.app_wallet.virtual_wallet.dto;

public class BenefitDTO {
    private String title;
    private boolean active;

    public BenefitDTO(String title, boolean active) {
        this.title = title;
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }
}

