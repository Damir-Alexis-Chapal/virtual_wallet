package com.app_wallet.virtual_wallet.dto;

public class BenefitDTO {
    private Long id;
    private String title;
    private int pointsRequired;

    public BenefitDTO() {}

    public BenefitDTO(Long id, String title, int pointsRequired) {
        this.id = id;
        this.title = title;
        this.pointsRequired = pointsRequired;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getPointsRequired() { return pointsRequired; }
    public void setPointsRequired(int pointsRequired) { this.pointsRequired = pointsRequired; }
}
