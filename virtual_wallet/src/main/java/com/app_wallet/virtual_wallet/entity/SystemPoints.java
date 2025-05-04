package com.app_wallet.virtual_wallet.entity;
import com.app_wallet.virtual_wallet.model.Range;

public class SystemPoints {
    private Long id;
    private int acumulatedPoints;
    private Range range;
    private User user;

    public SystemPoints() {
    }

    public SystemPoints(Long id, int acumulatedPoints, Range range, User user) {
        this.id = id;
        this.acumulatedPoints = acumulatedPoints;
        this.range = range;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAcumulatedPoints() {
        return acumulatedPoints;
    }

    public void setAcumulatedPoints(int acumulatedPoints) {
        this.acumulatedPoints = acumulatedPoints;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

