package com.app_wallet.virtual_wallet.dto;

public class RangeDTO {
    private Long id;
    private String name;
    private int minPoints;
    private int maxPoints;

    public RangeDTO() {}

    public RangeDTO(Long id, String name, int minPoints, int maxPoints) {
        this.id = id;
        this.name = name;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMinPoints() { return minPoints; }
    public void setMinPoints(int minPoints) { this.minPoints = minPoints; }

    public int getMaxPoints() { return maxPoints; }
    public void setMaxPoints(int maxPoints) { this.maxPoints = maxPoints; }
}
