package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.model.RangeUser;
import jakarta.persistence.*;

@Entity
public class SystemPointsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int accumulatedPoints;

    @Enumerated(EnumType.STRING) // Suponiendo que Range es un enum
    private RangeUser rangeUser;

    public SystemPointsEntity() {
    }

    public SystemPointsEntity(Long id, int accumulatedPoints, RangeUser rangeUser) {
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

    public RangeUser getRange() {
        return rangeUser;
    }

    public void setRange(RangeUser rangeUser) {
        this.rangeUser = rangeUser;
    }
}
