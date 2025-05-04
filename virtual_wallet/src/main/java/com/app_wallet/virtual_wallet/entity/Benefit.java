package com.app_wallet.virtual_wallet.entity;

public class Benefit {
    private Long id;
    private String title;
    private String description;
    private int pointsRequired;
    private boolean isActive;

    public Benefit() {
    }

    public Benefit(Long id, String title, String description, int pointsRequired, boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pointsRequired = pointsRequired;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPointsRequired() {
        return pointsRequired;
    }

    public void setPointsRequired(int pointsRequired) {
        this.pointsRequired = pointsRequired;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
