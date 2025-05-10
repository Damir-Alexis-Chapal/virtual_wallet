package com.app_wallet.virtual_wallet.dto;

public class SystemPointsDTO {
    private Long id;
    private int accumulatedPoints;

    public SystemPointsDTO() {}

    public SystemPointsDTO(Long id, int accumulatedPoints) {
        this.id = id;
        this.accumulatedPoints = accumulatedPoints;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getAccumulatedPoints() { return accumulatedPoints; }
    public void setAccumulatedPoints(int accumulatedPoints) { this.accumulatedPoints = accumulatedPoints; }
}
