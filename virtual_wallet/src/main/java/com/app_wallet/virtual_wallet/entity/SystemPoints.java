package com.app_wallet.virtual_wallet.entity;
import com.app_wallet.virtual_wallet.model.Range;

public class SystemPoints {
    private Long id;
    private int accumulatedPoints;
    private Range range;

    public SystemPoints() {
    }

    public SystemPoints(Long id, int accumulatedPoints, Range range) {
        this.id = id;
        this.accumulatedPoints = accumulatedPoints;
        this.range = range;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void setAccumulatedPoints(int accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}
