package com.app_wallet.virtual_wallet.entity;

public class RangeEntity {
    private Long id;
    private String name;   // Nombre del rango (Bronce, Plata, etc.)
    private int minPoints; // Puntos mínimos para estar en este rango
    private int maxPoints; // Puntos máximos para este rango

    public RangeEntity() {
    }

    public RangeEntity(Long id, String name, int minPoints, int maxPoints) {
        this.id = id;
        this.name = name;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
}
