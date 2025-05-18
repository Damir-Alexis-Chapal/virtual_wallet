package com.app_wallet.virtual_wallet.dto;

import com.app_wallet.virtual_wallet.model.RangeUser;

public class SystemPointsDTO {
    private Long id;
    private int accumulatedPoints;
    private RangeUser rangeUser;

    public SystemPointsDTO() {}

    public SystemPointsDTO(Long id, int accumulatedPoints, RangeUser rangeUser) {
        this.id = id;
        this.accumulatedPoints = accumulatedPoints;
        this.rangeUser = rangeUser;
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

    public RangeUser getRangeUser() {
        return rangeUser;
    }

    public void setRangeUser(RangeUser rangeUser) {
        this.rangeUser = rangeUser;
    }
}
