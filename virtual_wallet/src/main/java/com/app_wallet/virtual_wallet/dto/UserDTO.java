package com.app_wallet.virtual_wallet.dto;

import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private SystemPointsEntity systemPoints;
    private List<Long> accountIds;
    private List<Long> notificationIds;


    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, SystemPointsEntity systemPoints) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.systemPoints = systemPoints;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SystemPointsEntity getSystemPoints() {
        return systemPoints;
    }

    public void setSystemPoints(SystemPointsEntity systemPoints) {
        this.systemPoints = systemPoints;
    }

    public List<Long> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(List<Long> accountIds) {
        this.accountIds = accountIds;
    }

    public List<Long> getNotificationIds() {
        return notificationIds;
    }

    public void setNotificationIds(List<Long> notificationIds) {
        this.notificationIds = notificationIds;
    }
}