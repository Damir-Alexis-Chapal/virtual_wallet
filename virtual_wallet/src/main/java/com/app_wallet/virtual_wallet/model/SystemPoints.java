package com.app_wallet.virtual_wallet.model;

import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;

public class SystemPoints implements Comparable<SystemPoints> {
    private final Long userId;
    private int accumulatedPoints;

    public SystemPoints(SystemPointsEntity e) {
        this.userId          = e.getUser().getId();
        this.accumulatedPoints = e.getAccumulatedPoints();
    }

    public Long getUserId() {
        return userId;
    }

    public int getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void addPoints(int delta) {
        this.accumulatedPoints = Math.max(0, this.accumulatedPoints + delta);
    }

    @Override
    public int compareTo(SystemPoints o) {
        return Integer.compare(this.accumulatedPoints, o.accumulatedPoints);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SystemPoints)) return false;
        return this.userId.equals(((SystemPoints)o).userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
