package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.model.RangeUser;
import jakarta.persistence.*;

@Entity
@Table(name = "system_points")
public class SystemPointsEntity implements Comparable<SystemPointsEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accumulated_points", nullable = false)
    private int accumulatedPoints;

    @Enumerated(EnumType.STRING)
    @Column(name = "range_user", nullable = false)
    private RangeUser rangeUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public SystemPointsEntity() {
        this.accumulatedPoints = 0;
        this.rangeUser = RangeUser.BRONZE;
    }

    public SystemPointsEntity(UserEntity user) {
        this.user = user;
        this.accumulatedPoints = 0;
        this.rangeUser = RangeUser.BRONZE;
    }

    public SystemPointsEntity(Long id, int accumulatedPoints, RangeUser rangeUser, UserEntity user) {
        this.id = id;
        this.accumulatedPoints = accumulatedPoints;
        this.rangeUser = rangeUser;
        this.user = user;
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
        this.rangeUser = calculateRange(accumulatedPoints);
    }

    public RangeUser getRangeUser() {
        return rangeUser;
    }
    public void setRangeUser(RangeUser rangeUser) {
        this.rangeUser = rangeUser;
    }

    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void addPoints(int points) {
        this.accumulatedPoints = Math.max(0, this.accumulatedPoints + points);
        this.rangeUser = calculateRange(this.accumulatedPoints);
    }

    private RangeUser calculateRange(int pts) {
        if (pts > 5000)    return RangeUser.PLATINUM;
        if (pts >= 1001)   return RangeUser.GOLD;
        if (pts >=  501)   return RangeUser.SILVER;
        return RangeUser.BRONZE;
    }

    @Override
    public int compareTo(SystemPointsEntity o) {
        return Integer.compare(this.accumulatedPoints, o.accumulatedPoints);
    }
}
